package com.watea.creditservice;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.watea.creditservice.pungueado.CMSDataGenerator;

import https.wsaa_afip_gov_ar.ws.services.logincms.LoginCMS;
import https.wsaa_afip_gov_ar.ws.services.logincms.LoginCMSService;
import wsfecred.afip.gob.ar.fecredservice.AuthRequestType;
import wsfecred.afip.gob.ar.fecredservice.ConsultarComprobanteRequestType;
import wsfecred.afip.gob.ar.fecredservice.FECredService;
import wsfecred.afip.gob.ar.fecredservice.FECredServicePortType;

public class AfipLoginTest {

	String basePath;

	@Test
	public void loginPurgado() {
		setBasePathDesdeCasa(false);

		try {
			//el "crt" es un DER
			String certPath = basePath + "watea.crt";
			//y el "der" (la key) no es un DER
			String certPass = basePath + "watea-key.der";
			String cuit = "30710037767";
			String destination = "CN=wsaa, O=AFIP, C=AR, SERIALNUMBER=CUIT 33693450239";
			String service = "wsfecred";

			String cmsData = new String(Base64.encodeBase64(new CMSDataGenerator(certPath, certPass, cuit, destination, service).getCMSData()));

			LoginCMS login = new LoginCMSService().getLoginCms();
			String response = login.loginCms(cmsData);

			response = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<loginTicketResponse version=\"1\">" + "    <header>"
					+ "        <source>CN=wsaa, O=AFIP, C=AR, SERIALNUMBER=CUIT 33693450239</source>"
					+ "        <destination>SERIALNUMBER=CUIT 30710037767, CN=watea_2019</destination>"
					+ "        <uniqueId>3228106662</uniqueId>" + "        <generationTime>2019-05-29T14:08:16.957-03:00</generationTime>"
					+ "        <expirationTime>2019-05-30T02:08:16.957-03:00</expirationTime>" + "    </header>" + "    <credentials>"
					+ "        <token>PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8c3NvIHZlcnNpb249IjIuMCI+CiAgICA8aWQgc3JjPSJDTj13c2FhLCBPPUFGSVAsIEM9QVIsIFNFUklBTE5VTUJFUj1DVUlUIDMzNjkzNDUwMjM5IiB1bmlxdWVfaWQ9IjM2MDA5NDg5MjkiIGdlbl90aW1lPSIxNTU5MTQ5NjM2IiBleHBfdGltZT0iMTU1OTE5Mjg5NiIvPgogICAgPG9wZXJhdGlvbiB0eXBlPSJsb2dpbiIgdmFsdWU9ImdyYW50ZWQiPgogICAgICAgIDxsb2dpbiBlbnRpdHk9IjMzNjkzNDUwMjM5IiBzZXJ2aWNlPSJ3c2ZlY3JlZCIgdWlkPSJTRVJJQUxOVU1CRVI9Q1VJVCAzMDcxMDAzNzc2NywgQ049d2F0ZWFfMjAxOSIgYXV0aG1ldGhvZD0iY21zIiByZWdtZXRob2Q9IjIyIj4KICAgICAgICAgICAgPHJlbGF0aW9ucz4KICAgICAgICAgICAgICAgIDxyZWxhdGlvbiBrZXk9IjMwNzEwMDM3NzY3IiByZWx0eXBlPSI0Ii8+CiAgICAgICAgICAgIDwvcmVsYXRpb25zPgogICAgICAgIDwvbG9naW4+CiAgICA8L29wZXJhdGlvbj4KPC9zc28+Cg==</token>"
					+ "        <sign>tthyiM1DE+ybY/Ll2d7otFPewPYxajlATudzyatDgBqM12neoa2HTIOg364QGgn2KorBw4pw8MXNoBxEh50hcs3/q6DY3gHwHCvVllUO4YmXLHo3g+oCgy4JYulI5R2fZAdQN08qN/dNsB1zgPaxp3p/x0UUadtrWGIa9WRetPM=</sign>"
					+ "    </credentials>" + "</loginTicketResponse>";

			Document xmlResponse = convertStringToXMLDocument(response);

			AuthRequestType authRequest = new AuthRequestType();
			authRequest.setCuitRepresentada(Long.valueOf(cuit));

			FECredServicePortType feCredService = new FECredService().getFECredServiceSOAP();
			ConsultarComprobanteRequestType request = new ConsultarComprobanteRequestType();
			request.setAuthRequest(authRequest);
			feCredService.consultarComprobantes(request).getConsultarCmpReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Document convertStringToXMLDocument(String xmlString) {
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setBasePathDesdeCasa(boolean desdeCasa) {
		if (desdeCasa) {
			basePath = "C:\\Users\\Farguito\\Desktop\\credit-service\\src\\test\\resources\\";
		} else {
			basePath = "/Users/Fernando/fer/credit-service/src/test/resources/";
		}
	}

}
