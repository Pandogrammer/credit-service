/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

/**
 * Se ocupa del manejo de la encriptación utilizada con el servicio de
 * autenticación (WSAA) de la AFIP
 *
 * @author Jorge Argibay(jorge.argibay@watea.com.ar)
 * @version 1.0
 * @since 1.0
 */
public class CMSDataGenerator // extends Thread
{

    /**
     * Class logger
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * El web service de negocio para el que se esta autenticando
     */
    String afipWS;

    /**
     * PKCS#12 KeyStore
     */
    KeyStore keyStore;
    /**
     * La clave privada correspondiente al CUIT de la empresa.
     */
    PrivateKey key;
    /**
     * El certificado de la empresa.
     */
    X509Certificate certificate;
    /**
     * El nombre completo de la empresa, como figura en el certificado.
     */
    String empresaDN;
    /**
     * Los certificados, en una collection de Bounty Castle.
     */
    CertStore certStore;

    /**
     * XMLBodyMaker, utilizado para generar el xml que se usa firma digitalmente
     */
    XMLBodyMaker XMLMaker;

    /**
     * Inicializa los certificados.
     *
     * @param pkcs12CertificatePath Este es el path completo donde se encuentra
     * el certificado emitido por la AFIP para cada CUIT de la empresa.
     * @param pkcs12CertificateKeyPath El password que desencripta las claves y
     * los certificados.
     * @param CUIT Usamos el número de CUIT de la empresa como identificador de
     * la misma en el key store para obtener las claves.
     *
     * @since 1.0
     *
     */
    public CMSDataGenerator(String pkcs12CertificatePath, String pkcs12CertificateKeyPath, String CUIT, String destinationDN, String serviceName) throws Exception {
        log.entering(this.getClass().getName(), "Constructor");
        try {

            loadKeyStore(pkcs12CertificatePath, CUIT);

            loadKeys(CUIT, pkcs12CertificateKeyPath);

            this.afipWS = serviceName;

            this.XMLMaker = new XMLBodyMaker(this.empresaDN, destinationDN, this.afipWS);

            log.exiting(this.getClass().getName(), "Constructor");
            return;
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "Constructor", e);
            throw (e);
        }
    }

    /**
     * Lee un archivo en formato PKCS#12 desde el file system, y lo abre con el
     * password ingresado. El resultado lo guarda en la componente keyStore.
     *
     * @param pkcs12CertificatePath Este es el path completo donde se encuentra
     * el certificado emitido por la AFIP para cada CUIT de la empresa.
     * @param storePassword El password que desencripta las claves y los
     * certificados.
     *
     * @exception Exception La excepción que levanta.
     * @since 1.0
     *
     */
    private void loadKeyStore(String pkcs12CertificatePath, String CUIT) throws Exception {

        // Get keystore
        log.entering(this.getClass().getName(), "loadKeyStore");
        try {
            // Create a keystore using keys from the pkcs#12 p12file
            this.keyStore = KeyStore.getInstance("JKS");
            this.keyStore.load(null);
            FileInputStream p12stream = new FileInputStream(pkcs12CertificatePath);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate ca = (X509Certificate) cf.generateCertificate(p12stream);
            this.keyStore.setCertificateEntry(CUIT, ca);
            p12stream.close();
            log.exiting(this.getClass().getName(), "loadKeyStore");
            return;
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "loadKeyStore", e);
            throw (e);
        }
    }

    /**
     * Obtiene la clave privada y los certificados.
     *
     * @param CUIT Usamos el número de CUIT de la empresa como identificador de
     * la misma en el key store para obtener las claves.
     * @param storePassword El password que desencripta las claves y los
     * certificados.
     *
     * @exception Exception La excepción que levanta.
     * @since 1.0
     *
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {

        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private void loadKeys(String CUIT, String pkcs12CertificateKeyPath) throws Exception {
        log.entering(this.getClass().getName(), "loadKeys", CUIT);
        try {
            log.info("loadKeys : Keystore " + this.keyStore);
            log.finest("loadKeys : storePassword " + pkcs12CertificateKeyPath);
            // Get Certificate & Private key from KeyStore
            this.key = getPrivateKey(pkcs12CertificateKeyPath);
            //this.key = (PrivateKey) this.keyStore.getKey(CUIT, null/*storePassword.toCharArray()*/);
            log.info("loadKeys : Key " + this.key);
            this.certificate = (X509Certificate) this.keyStore.getCertificate(CUIT);
            log.info("loadKeys : Certificate " + this.certificate);
            this.empresaDN = certificate.getSubjectDN().toString();
            log.info("loadKeys : empresaDN " + this.empresaDN);

            // Create a list of Certificates to include in the final CMS
            ArrayList<X509Certificate> certList = new ArrayList<X509Certificate>();
            certList.add(this.certificate);

            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }

            this.certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");

            log.exiting(this.getClass().getName(), "loadKeys");
            return;
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "loadKeys", e);
            throw (e);
        }
    }

    public byte[] getCMSData() throws Exception {
        log.entering(this.getClass().getName(), "getCMSData");
        byte[] cmsData;
        try {
            String DIGEST_SHA1 = "SHA1withRSA";
            String BC_PROVIDER = "BC";
            // Create a new empty CMS Message
            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

            // Add a Signer to the Message
            List certList = new ArrayList();
            certList.add(this.certificate);
            Store certs = new JcaCertStore(certList);
            ContentSigner sha1Signer = new JcaContentSignerBuilder(DIGEST_SHA1)
                    .setProvider(BC_PROVIDER)
                    .build(this.key);
            gen.addSignerInfoGenerator(
                    new JcaSignerInfoGeneratorBuilder(
                            new JcaDigestCalculatorProviderBuilder()
                                    .setProvider(BC_PROVIDER)
                                    .build())
                            .build(sha1Signer, this.certificate));

            //gen.addSigner(this.key, this.certificate, CMSSignedDataGenerator.DIGEST_SHA1);
            // Add the Certificate to the Message
            gen.addCertificates(certs);
            //gen.addCertificatesAndCRLs(this.certStore);

            // Add the data (XML) to the Message
            CMSTypedData data = new CMSProcessableByteArray(XMLMaker.getData());
            // CMSProcessable data = new CMSProcessableByteArray(XMLMaker.getData());

            // Add a Sign of the Data to the Message
            CMSSignedData signed = gen.generate(data, true);
            //CMSSignedData signed = gen.generate(data, true, "BC");

            cmsData = signed.getEncoded();

            log.exiting(this.getClass().getName(), "getCMSData", cmsData);
            return (cmsData);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "getCMSData", e);
            throw (e);
        }
    }
}
