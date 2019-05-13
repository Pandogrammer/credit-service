/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.watea.creditservice.watea.agip.entities.TBCOMPROBANTE;
import com.watea.creditservice.watea.agip.entities.TBError;

/**
 *
 * @author fscippo
 */
public class COTService {

    private Logger log = Logger.getLogger(this.getClass().getName());

    String token; //token
    String sign; //sign

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    String serviceURL;

    JAXBContext jaxbContext;
    Unmarshaller unmarshaller;
    Marshaller marshaller;

    public COTService() {
        try {
            this.jaxbContext = JAXBContext.newInstance("ar.com.watea.agip.entities");

            this.unmarshaller = jaxbContext.createUnmarshaller();

            this.marshaller = jaxbContext.createMarshaller();
            this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        } catch (JAXBException ex) {
            log.severe(ex.getMessage());
        }
    }

    public COTResponse processFile(String processFile, String fileName) throws Exception {

        System.out.println("Procesando archivo: " + processFile);
        log.info("processFile: " + processFile);
        log.info("fileName: " + fileName);
        COTResponse response = new COTResponse();
        File remittanceFile = new File(processFile);
        File textFile = new File(fileName + ".txt");

        try {

            log.info("Procesando archivo: " + remittanceFile.getName());
            log.info("Enviando archivo: " + textFile.getName());

            FileChannel source = null;
            FileChannel destination = null;

            try {
                source = new FileInputStream(remittanceFile).getChannel();
                destination = new FileOutputStream(textFile).getChannel();
                destination.transferFrom(source, 0, source.size());
            } finally {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            }

            String boundary = Long.toHexString(System.currentTimeMillis());
            URLConnection conn = new URL(serviceURL).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            ///////////////CONN///////////////
            OutputStream output = conn.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
            String CRLF = "\r\n";
            String charset = "ISO-8859-1";
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"token\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(token).append(CRLF).flush();
            ///////////////PASSWORD/////////////////
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"sign\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(sign).append(CRLF).flush();
            ////////////////FILE////////////////////
            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            Files.copy(textFile.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush();
            ///////////////PARAMETERS ENDS///////////////

            ///////////MOSTRAR XML START//////////
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String outputString;
            while ((outputString = br.readLine()) != null) {
                sb.append(new String(outputString.getBytes("ISO-8859-1")));
            }
            ///////////MOSTRAR XML ENDS//////////

            ///////////JAXB START//////////
            try {

                File f = File.createTempFile("temp_file", "xml");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(sb.toString());
                bw.close();

                response.setComprobante((TBCOMPROBANTE) unmarshaller.unmarshal(f));

            } catch (Exception e) {
                try {
                    File f = File.createTempFile("temp_file", "xml");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    bw.write(sb.toString());
                    bw.close();

                    response.setError((TBError) unmarshaller.unmarshal(f));

                } catch (Exception ex) {
                    System.out.println("Error when parsing response error: " + ex.getMessage());
                    //log.severe("Error when parsing response error: "+ex.getMessage());
                    log.info("Error when parsing response error: " + ex.getMessage());
                }
            }
            ///////////JAXB ENDS//////////

        } catch (Exception ex) {
            throw ex;
        }

        textFile.delete();

        System.out.println("Archivo procesado.");
        return response;

    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

}
