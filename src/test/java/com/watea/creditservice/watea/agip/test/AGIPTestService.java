package com.watea.creditservice.watea.agip.test;

import ar.gov.agip.cc.controller.soap.LoginTicketResponse;
import ar.gov.agip.cc.controller.soap.ObjectFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class AGIPTestService {

    PrintStream out = System.out;

    String action = "getLoginTicketFromCMS";
    String webserviceTest = "https://desa.agip.gob.ar/claveciudad/webservice/LoginWS";

    public LoginTicketResponse getLoginTicketFromCMS(String cms) {
        try {
            byte[] reqBytes = generateSoapMessage(cms).getBytes();

            // Creating the HttpURLConnection object
            URL oURL = new URL(webserviceTest);
            HttpURLConnection con
                    = (HttpURLConnection) oURL.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty(
                    "Content-type", "text/xml; charset=utf-8");
            con.setRequestProperty("SOAPAction", action);
            con.setDoOutput(true);
            con.setDoInput(true);

            // Posting the SOAP request XML message
            OutputStream reqStream = con.getOutputStream();
            reqStream.write(reqBytes);

            // Reading the SOAP response XML message
            InputStream in = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            String response = result.toString();

            int startIndex = response.indexOf("<loginTicketResponse version=\"1.0\">");
            int endIndex = response.indexOf("</loginTicketResponse>", startIndex);
            endIndex = endIndex + "</loginTicketResponse>".length();
            if (endIndex == -1) {
                endIndex = response.length();
            }

            LoginTicketResponse loginTicketResponse = parseTicketResponse(response.substring(startIndex, endIndex));

            return loginTicketResponse;

        } catch (IOException e) {
            Logger.getLogger(AGIPTestService.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    private String generateSoapMessage(String cms) {

        String soapMessage = ""
                + "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "<Body>\n"
                + "<getLoginTicketFromCMS xmlns=\"http://soap.controller.cc.agip.gov.ar/\">\n"
                + "<CMS xmlns=\"\">" + cms + "</CMS>\n"
                + "</getLoginTicketFromCMS>\n"
                + "</Body>\n"
                + "</Envelope>";

        return soapMessage;
    }

    private LoginTicketResponse parseTicketResponse(String response) {
        try {
            //Genero el XML de respuesta
            response = "<?xml version='1.0' encoding='UTF-8'?>" + response;
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(response));
            Document doc = db.parse(is);
            doc.getDocumentElement().normalize();

            //Genero el LoginTicketResponse
            ObjectFactory of = new ObjectFactory();
            LoginTicketResponse ltr = of.createLoginTicketResponse();

            ltr.setCredentials(of.createCredenciales());
            ltr.setHeader(of.createHeaderLogin());

            ltr.getCredentials().setSign(doc.getElementsByTagName("sign").item(0).getTextContent());
            ltr.getCredentials().setToken(doc.getElementsByTagName("token").item(0).getTextContent());

            ltr.getHeader().setDestination(doc.getElementsByTagName("destination").item(0).getTextContent());
            ltr.getHeader().setSource(doc.getElementsByTagName("source").item(0).getTextContent());
            ltr.getHeader().setUniqueId(Long.parseLong(doc.getElementsByTagName("uniqueId").item(0).getTextContent()));

            ltr.getHeader().setExpirationTime(
                    DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(
                                    doc.getElementsByTagName("expirationTime").item(0).getTextContent()));

            ltr.getHeader().setGenerationTime(
                    DatatypeFactory.newInstance()
                            .newXMLGregorianCalendar(
                                    doc.getElementsByTagName("generationTime").item(0).getTextContent()));

            return ltr;
        } catch (Exception ex) {
            Logger.getLogger(AGIPTestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
