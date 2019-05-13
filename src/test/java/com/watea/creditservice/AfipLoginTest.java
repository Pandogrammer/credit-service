package com.watea.creditservice;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.junit.Test;

import com.watea.creditservice.pungueado.HomoSetupDao;
import com.watea.creditservice.pungueado.ProductionSetupDao;
import com.watea.creditservice.pungueado.Service;
import com.watea.creditservice.pungueado.SetupDao;
import com.watea.creditservice.pungueado.WsaaManager;
import com.watea.creditservice.watea.agip.WSLoginManager;

import ar.com.system.afip.wsaa.business.api.XmlConverter;
import ar.com.system.afip.wsaa.data.api.CompanyInfo;
import ar.com.system.afip.wsaa.data.api.TaxCategory;
import ar.com.system.afip.wsaa.data.api.WsaaDao;
import ar.com.system.afip.wsaa.data.impl.InMemoryWsaaDao;
import https.wsaa_afip_gov_ar.ws.services.logincms.LoginCMS;
import https.wsaa_afip_gov_ar.ws.services.logincms.LoginCMSService;

public class AfipLoginTest {

	private String exampleXml = "<?xml version=\"1.0\" encoding=\"UTF\u00AD8\"?>" + "<loginTicketRequest version=\"1.0\">  " + "<header>"
			+ "<source>cn=srv1,ou=facturacion,o=empresa s.a.,c=ar,serialNumber=CUIT 30123456789</source>"
			+ "<destination>cn=wsaa,o=afip,c=ar,serialNumber=CUIT 33693450239</destination>" + "<uniqueId>4325399</uniqueId>    "
			+ "<generationTime>2001\u00AD12\u00AD31T12:00:00\u00AD03:00</generationTime>"
			+ "<expirationTime>2001\u00AD12\u00AD31T12:10:00\u00AD03:00</expirationTime>" + "</header>" + "<service>wsfe</service>"
			+ "</loginTicketRequest>";

	@Test
	public void loginTicketRequestXML() {
//		generateXML()
	}

	@Test
	public void build_keys() {
		buildKeys();

	}

	@Test
	public void loginLibreriaExterna() {
		SetupDao setupDao = new ProductionSetupDao(Service.WSFECRED);
		WsaaDao daoWsaa = new InMemoryWsaaDao();
		LoginCMS loginCms = new LoginCMSService().getLoginCms();
		XmlConverter xmlConverter = getXmlConverter();

		WsaaManager wsaaManager = new WsaaManager(daoWsaa, setupDao, loginCms, xmlConverter);

		daoWsaa.saveCompanyInfo(getCompanyInfo());

		wsaaManager.initializeKeys();
		wsaaManager.updateCertificate(getCertificate());
		wsaaManager.login(Service.WSFECRED);

	}

	@Test
	public void loginWatea(){
		try {
			String certPath = "/Users/Fernando/fer/credit-service/src/test/resources/watea.crt";
			String certPass = "/Users/Fernando/fer/credit-service/src/test/resources/watea.priv";
			String cuit = "1234";
			String destination = "wsfecred";
			WSLoginManager loginManager = new WSLoginManager(certPath, certPass, cuit, destination);

			String service = "wsfecred";
			loginManager.getCredential(service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CompanyInfo getCompanyInfo() {
		Serializable id = 1;
		String name = "Watea";
		boolean active = true;
		String unit = "";
		String cuit = "20239686673";
		String publicKey = null; //null
		String privateKey = null; //null
		String certificate = null; //null
		String grossIncome = "";
		Date activityStartDate = new Date();
		TaxCategory taxCategory = TaxCategory.RESPONSABLE_INSCRIPTO;
		String address = null; //null
		String location = null; //null
		String alias = "Watea";
		return new CompanyInfo(id, name, active, unit, cuit, publicKey, privateKey,
				certificate, grossIncome, activityStartDate, taxCategory, address, location, alias);
	}



	private String getCertificate() {
		return "-----BEGIN CERTIFICATE-----\n" + "MIIDSzCCAjOgAwIBAgIIZIPy3wtgW0cwDQYJKoZIhvcNAQENBQAwODEaMBgGA1UEAwwRQ29tcHV0\n"
				+ "YWRvcmVzIFRlc3QxDTALBgNVBAoMBEFGSVAxCzAJBgNVBAYTAkFSMB4XDTE5MDUwNzExNTM0NVoX\n"
				+ "DTIxMDUwNjExNTM0NVowMTEUMBIGA1UEAwwLV2F0ZWFGRUNSRUQxGTAXBgNVBAUTEENVSVQgMjAy\n"
				+ "Mzk2ODY2NzMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCtfrFC/4YOkO1W6vYeCs1C\n"
				+ "PfIwntm0opCkxI8/MfDZ9JxZ3UE1AvRQ+cqBBhHWzRFTvY2IIxunTPtryqiTvcrrTQ9+GQYbJdOB\n"
				+ "+kiWqhIE2dLNbgTwh4Coi1lGAjVPmaJjpEiUI0IAK3ptlTCOpL9kfzFFJVLI1nDaUCKjSbJn/ykF\n"
				+ "lKttfFcF8MD09/URASYQpEyC82m2M+2C7d1xsHUkg8W/ado8yMASTmHQ75BG4ygSp0IDFPWoqDJr\n"
				+ "WNph7Xy6XKCGGfjk4rlAUfoEZEw+UFONulLFtyMVj2oXxqfRIptU2lY0uD7XvtPqMXFWHToBfH18\n"
				+ "tYZ9cxHY6KiqYbg7AgMBAAGjYDBeMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUs7LT//3put7e\n"
				+ "ja8RIZzWIH3yT28wHQYDVR0OBBYEFIYD3UGXXOqHHahk6/jEuO2FqQRzMA4GA1UdDwEB/wQEAwIF\n"
				+ "4DANBgkqhkiG9w0BAQ0FAAOCAQEAMpLJYybrM1iA5dxKN4z4cJR7jiFm8P0Y0Ya31xjJnXYmWueZ\n"
				+ "qtjris1mLRFkSF677c5azNfzRM/YXGmF7d1KbKnNFFAkUGvJ6UVWwL9xdLanu4A1/ZXOVaqzwyEm\n"
				+ "aYc9WI9bJ9owMj87GNAz6/z0q/kan97yLC6UwwKeNJWmk5U2iZ6VJ7SHgxiIjRI1WTxVkq8Apvfl\n"
				+ "NZsguyQ8/UePKpkgk97fpjEoEITv1Kw5BObuMCOK7qxXlE2fuIEWPjcVOLelwMw3/qNQSqLPJi7h\n"
				+ "5ju1MweBMPIPKE+3H6RtvEbsdvpB0Xu0+Y4Zq1NJ8DlIp80zMiectjl7dKA/74NQsA==\n" + "-----END CERTIFICATE-----";
	}

	private XmlConverter getXmlConverter() {
		return new XmlConverter() {
			@Override
			public String toXml(Object data) {
				return exampleXml;
			}

			@Override
			public <T> T fromXml(Class<T> clazz, String data) {
				return null;
			}
		};
	}

	private static KeyPair buildKeys() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			return keyGen.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}


}
