package com.watea.creditservice.pungueado;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.inject.Inject;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import com.google.common.base.Throwables;

import ar.com.system.afip.wsaa.business.api.XmlConverter;
import ar.com.system.afip.wsaa.business.impl.LoginTicketResponse;
import ar.com.system.afip.wsaa.data.api.CompanyInfo;
import ar.com.system.afip.wsaa.data.api.WsaaDao;
import ar.com.system.afip.wsaa.service.api.Credentials;
import https.wsaa_afip_gov_ar.ws.services.logincms.LoginCMS;
import https.wsaa_afip_gov_ar.ws.services.logincms.LoginFault_Exception;

public class WsaaManager {
	static final String SIGNING_ALGORITHM = "SHA512withRSA";
	private final WsaaDao wsaaDao;
	private final SetupDao setupDao;
	private final LoginCMS loginCms;
	private final XmlConverter xmlConverter;

	@Inject
	public WsaaManager(WsaaDao wsaaDao,
			SetupDao setupDao,
			LoginCMS loginCms,
			XmlConverter xmlConverter) {
		this.wsaaDao = checkNotNull(wsaaDao);
		this.setupDao = checkNotNull(setupDao);
		this.loginCms = checkNotNull(loginCms);
		this.xmlConverter = checkNotNull(xmlConverter);
	}

	public void initializeKeys() {
		try {
			CompanyInfo info = wsaaDao.loadActiveCompanyInfo();
			KeyPair keyPair = buildKeys();
			wsaaDao.saveCompanyInfo(new CompanyInfo(info.getId(),
					info.getName(),
					info.isActive(),
					info.getUnit(),
					info.getCuit(),
					toPem(keyPair.getPublic()),
					toPem(keyPair.getPrivate()),
					null,
					info.getGrossIncome(),
					info.getActivityStartDate(),
					info.getTaxCategory(),
					info.getAddress(),
					info.getLocation(),
					info.getAlias()));
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

	public String buildCertificateRequest() {
		try {
			CompanyInfo companyInfo = wsaaDao.loadActiveCompanyInfo();

			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();

			PEMKeyPair pemPrivateKey = fromPem(companyInfo.getPrivateKey());
			PrivateKey privateKey = converter.getPrivateKey(pemPrivateKey
					.getPrivateKeyInfo());
			PEMKeyPair pemPublicKey = fromPem(companyInfo.getPrivateKey());
			PublicKey publicKey = converter.getPublicKey(pemPublicKey
					.getPublicKeyInfo());

			X500Principal subject = new X500Principal(companyInfo.certificateSource());
			ContentSigner signGen = new JcaContentSignerBuilder(SIGNING_ALGORITHM)
					.build(privateKey);

			PKCS10CertificationRequest csr = new JcaPKCS10CertificationRequestBuilder(
					subject, publicKey).build(signGen);

			return toPem(csr);
		} catch (IOException | OperatorCreationException e) {
			throw Throwables.propagate(e);
		}
	}

	public void updateCertificate(String certificate) {
		checkNotNull(certificate);
		CompanyInfo info = wsaaDao.loadActiveCompanyInfo();
		wsaaDao.saveCompanyInfo(new CompanyInfo(info.getId(),
				info.getName(),
				info.isActive(),
				info.getUnit(),
				info.getCuit(),
				info.getPublicKey(),
				info.getPrivateKey(),
				certificate,
				info.getGrossIncome(),
				info.getActivityStartDate(),
				info.getTaxCategory(),
				info.getAddress(),
				info.getLocation(),
				info.getAlias()));
	}

	public Credentials login(Service service) {
		try {
			CompanyInfo companyInfo = wsaaDao.loadActiveCompanyInfo();
			checkNotNull(companyInfo.getName(),
					"Debe configurar el nombre de la empresa antes de realizar el login");
			checkNotNull(companyInfo.getUnit(),
					"Debe configurar la unidad oranizacional  antes de realizar el login");
			checkNotNull(companyInfo.getCuit(),
					"Debe configurar el CUIT antes de realizar el login");
			checkNotNull(companyInfo.getPrivateKey(),
					"Debe configurar la clave privada antes de realizar el login");
			checkNotNull(companyInfo.getPublicKey(),
					"Debe configurar la clave publica antes de realizar el login");
			checkNotNull(companyInfo.getCertificate(),
					"Debe configurar el certificado antes de realizar el login");

			X509CertificateHolder certificateHolder = fromPem(companyInfo
					.getCertificate());
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(
							certificateHolder.getEncoded()));

			PEMKeyPair pemKeyPair = fromPem(companyInfo.getPrivateKey());
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			PrivateKey privKey = converter.getPrivateKey(pemKeyPair
					.getPrivateKeyInfo());

			String cms = LoginTicketRequest
					.create(companyInfo.loginSource(),
							service,
							setupDao.readSetup()
									.getEnvironment())
					.toXml(xmlConverter)
					.toCms(certificate, privKey)
					.toString();

			System.out.println(cms);
			String loginTicketResponseXml = loginCms.loginCms(cms);

			LoginTicketResponse response = xmlConverter
					.fromXml(LoginTicketResponse.class, loginTicketResponseXml);

			return response.getCredentials();
		} catch (IOException | CertificateException | LoginFault_Exception e ) {
			throw Throwables.propagate(e);
		}
	}

	private static String toPem(Object data) throws IOException {
		try (StringWriter out = new StringWriter();
				JcaPEMWriter pem = new JcaPEMWriter(out)) {
			pem.writeObject(data);
			pem.flush();
			return out.toString();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T fromPem(String data) throws IOException {
		try (PEMParser parser = new PEMParser(new StringReader(data))) {
			return (T) parser.readObject();
		}
	}

	private static KeyPair buildKeys() {
		try {

			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			return keyGen.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw Throwables.propagate(e);
		}
	}
}
