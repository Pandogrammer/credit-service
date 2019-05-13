/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip;

import ar.gov.agip.cc.controller.soap.LoginTicketResponse;
import ar.gov.agip.cc.controller.soap.LoginWS;
import ar.gov.agip.cc.controller.soap.Webservice_002fLoginWS;
import com.watea.creditservice.watea.agip.test.AGIPTestService;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Hashtable;
import java.util.logging.Logger;

public class WSLoginManager // extends Thread
{

    Logger log = Logger.getLogger(this.getClass().getName());

    Hashtable dataGenerators;
    Hashtable activeCredentials;

    String pkcsCertPath;
    String pkcsCertPass;
    String dstDN;
    String empCUIT;

    String ambiente;

    /**
     * Inicializa la clase con los parámetros de la Unidad Operativa.
     *
     * @param pkcs12CertificatePath Este es el path completo donde se encuentra
     * el certificado emitido por la AFIP para cada CUIT de la empresa.
     * @param pkcs12CertificatePass Este es el password asociado al certificado
     * p12
     * @param CUIT CUIT de la empresa asociada
     * @param destinationDN DN del WSAA
     * @since 1.0
     *
     */
    public WSLoginManager(String pkcs12CertificatePath, String pkcs12CertificatePass, String CUIT, String destinationDN) throws Exception {
        log.entering(this.getClass().getName(), "Constructor");
        try {

            this.pkcsCertPath = pkcs12CertificatePath;
            this.pkcsCertPass = pkcs12CertificatePass;
            this.empCUIT = CUIT;
            this.dstDN = destinationDN;

            this.dataGenerators = new Hashtable<String, CMSDataGenerator>();
            this.activeCredentials = new Hashtable<String, Credential>();

            this.ambiente = COTRequestProcess.getProperties().getProperty("ambiente");

            log.exiting(this.getClass().getName(), "Constructor");
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "Constructor", e);
            throw (e);
        }
    }

    /**
     * Devuelve un CMSDataGenerator para cada web service de negocio de la AFIP.
     * Si no existe uno, lo crea pasandole la información de los certificados y
     * el servicio que se quiere acceder.
     *
     * @param servicename Nombre del webService de negocio que se quiere acceder
     * @return Retorna el CMS
     * @since 1.0
     *
     */
    public CMSDataGenerator getCMSDataGenerator(String servicename) throws Exception {
        log.entering(this.getClass().getName(), "CMSDataGenerator");
        try {

            if (this.dataGenerators.containsKey(servicename)) {
                // Existe CMSDataGenerator
                log.exiting(this.getClass().getName(), "CMSDataGenerator", (CMSDataGenerator) this.dataGenerators.get(servicename));
                return ((CMSDataGenerator) this.dataGenerators.get(servicename));

            } else {
                // No existe CMSDataGenerator para el servicename, lo genero y cargo en el hastable dataGenerators
                CMSDataGenerator cmsDataTemp = new CMSDataGenerator(pkcsCertPath, pkcsCertPass, empCUIT, dstDN, servicename);
                this.dataGenerators.put(servicename, cmsDataTemp);
                log.exiting(this.getClass().getName(), "CMSDataGenerator", cmsDataTemp);
                return (cmsDataTemp);

            }
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "CMSDataGenerator", e);
            throw (e);
        }
    }

    /**
     * Devuelve una nueva credencial de autenticación para el webservice de
     * negocio Si no existe uno, lo crea pasandole la información de los
     * certificados y el servicio que se quiere acceder. 1 - Obtener el
     * CMSDataGenerator correspondiente al servicio. 2 - Generar el XML 3 -
     * Invocar al web service WSAA 4 - Generar un nuevo Credential con la
     * información devuelta. 5 - Devolver la credencial generada.
     *
     * @param servicename Nombre del webService de negocio que se quiere acceder
     * @return Una nueva credencial, generada por WSAA
     * @since 1.0
     *
     */
    protected Credential getNewCredential(String servicename) throws Exception {
        log.entering(this.getClass().getName(), "getNewCredential");
        try {
            // Obtengo el CMSDataGenerator del correspondiente servicio
            byte[] cmsData = getCMSDataGenerator(servicename).getCMSData();

            // Envio el CMS en BASE64 y recibo el XML de respuesta
            LoginTicketResponse responseWS = getLoginTicketFromCMS(new String(Base64.encodeBase64(cmsData)));

            log.exiting(this.getClass().getName(), "getNewCredential", responseWS);

            // Devuelvo la credencial generada mediante el XML de respuesta
            return (new Credential(responseWS));

        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "getNewCredential", e);
            throw (e);
        }

    }

    private LoginTicketResponse getLoginTicketFromCMS(String cms) throws Exception {

        switch (ambiente) {

            case "PROD" : {
                // Invoco el webservice
                LoginWS login = new Webservice_002fLoginWS().getLoginWSPort();
                return login.getLoginTicketFromCMS(cms);
            }
            case "TEST": {
                // Invoco el webservice
                AGIPTestService login = new AGIPTestService();
                return login.getLoginTicketFromCMS(cms);
            }
            default: {                
                throw new Exception("Ambiente definido incorrectamente en el archivo de configuracion. (Debe ser 'PROD' o 'TEST')");
            }

        }
    }

    /**
     * Metodo getCredential
     *
     * Este método debe realizar lo siguiente: 1 - Fijarse si existe una
     * credencial para el webService de negocio 2 - Si esta vigente, devolverlo.
     * 3 - Si no esta vigente, genera uno nuevo, lo guarda en el hashtable y lo
     * devuelve.
     *
     * @param servicename Nombre del webService de negocio que se quiere acceder
     * @return Devuelve un TA firmado por la AFIP
     * @since 1.0
     *
     */
    public Credential getCredential(String servicename) throws Exception {
        log.entering(this.getClass().getName(), "getCredential");
        try {

            if (this.activeCredentials.containsKey(servicename)) {
                // Existe una credencial asociada al servicename
                Credential actualCredential = (Credential) this.activeCredentials.get(servicename);
                if (!actualCredential.isExpired()) {
                    // La credencial esta activa
                    log.exiting(this.getClass().getName(), "getCredential", actualCredential);
                    return (actualCredential);

                } else {
                    // La credencial expiro, genero una nueva y la guardo en el hashtable activeCredentials
                    Credential credentialsTemp = getNewCredential(servicename);
                    this.activeCredentials.put(servicename, credentialsTemp);
                    log.exiting(this.getClass().getName(), "getCredential", credentialsTemp);
                    return (credentialsTemp);
                }

            } else {
                // No exise una credencial asociada al servicio, la genero
                Credential credentialsTemp = getNewCredential(servicename);
                this.activeCredentials.put(servicename, credentialsTemp);
                log.exiting(this.getClass().getName(), "getCredential", credentialsTemp);
                return (credentialsTemp);
            }
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "getCredential", e);
            throw (new Exception("Problema al buscar la credencial debido a: " + e.getMessage(), e));
        }
    }

}
