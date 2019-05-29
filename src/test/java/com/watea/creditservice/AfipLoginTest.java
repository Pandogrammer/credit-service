package com.watea.creditservice;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

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

			LoginTicketResponse ticket = convertStringToXMLDocument(response);

			AuthRequestType authRequest = new AuthRequestType();
			authRequest.setCuitRepresentada(Long.valueOf(cuit));
			authRequest.setSign(ticket.credentials.getSign());
			authRequest.setToken(ticket.credentials.getToken());

			FECredServicePortType feCredService = new FECredService().getFECredServiceSOAP();
			ConsultarComprobanteRequestType request = new ConsultarComprobanteRequestType();
			request.setAuthRequest(authRequest);
			feCredService.consultarComprobantes(request).getConsultarCmpReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginTicketResponse convertStringToXMLDocument(String xmlString){
		try {
			JAXBContext jc = JAXBContext.newInstance(LoginTicketResponse.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			StreamSource streamSource = new StreamSource(new StringReader(xmlString));
			JAXBElement<LoginTicketResponse> je = unmarshaller.unmarshal(streamSource, LoginTicketResponse.class);

			return je.getValue();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}



	private void setBasePathDesdeCasa(boolean desdeCasa) {
		if (desdeCasa) {
			basePath = "C:\\Users\\Farguito\\Desktop\\credit-service\\src\\test\\resources\\";
		} else {
			basePath = "/Users/Fernando/fer/credit-service/src/test/resources/";
		}
	}

}
