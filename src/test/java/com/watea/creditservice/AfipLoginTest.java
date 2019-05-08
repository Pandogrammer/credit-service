package com.watea.creditservice;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import ar.com.system.afip.wsaa.business.api.Service;
import ar.com.system.afip.wsaa.business.api.XmlConverter;
import ar.com.system.afip.wsaa.business.impl.BouncyCastleWsaaManager;
import ar.com.system.afip.wsaa.data.api.SetupDao;
import ar.com.system.afip.wsaa.data.api.WsaaDao;
import ar.com.system.afip.wsaa.data.impl.HomoSetupDao;
import ar.com.system.afip.wsaa.service.api.LoginCMS;

public class AfipLoginTest {

	private String exampleXml = "<?xml version=\"1.0\" encoding=\"UTF\u00AD8\"?>" + "<loginTicketRequest version=\"1.0\">  " + "<header>"
			+ "<source>cn=srv1,ou=facturacion,o=empresa s.a.,c=ar,serialNumber=CUIT 30123456789</source>"
			+ "<destination>cn=wsaa,o=afip,c=ar,serialNumber=CUIT 33693450239</destination>" + "<uniqueId>4325399</uniqueId>    "
			+ "<generationTime>2001\u00AD12\u00AD31T12:00:00\u00AD03:00</generationTime>"
			+ "<expirationTime>2001\u00AD12\u00AD31T12:10:00\u00AD03:00</expirationTime>" + "</header>" + "<service>wsfe</service>"
			+ "</loginTicketRequest>";

	@Test
	public void loginTicketRequestXML() {
		generateXML()
	}

	@Test
	public void build_keys() {
		buildKeys();

	}

	@Test
	public void algoQuePuedeServir() {
		SetupDao setupDao = new HomoSetupDao(Service.WSFE);
		WsaaDao wsaaDao;
		LoginCMS loginCms;
		XmlConverter xmlConverter;

		BouncyCastleWsaaManager wsaaManager = new BouncyCastleWsaaManager(
				wsaaDao, setupDao, loginCms, xmlConverter);
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
