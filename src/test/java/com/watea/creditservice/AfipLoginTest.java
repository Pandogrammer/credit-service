package com.watea.creditservice;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class AfipLoginTest {

    private String exampleXml =
            "<?xml version=\"1.0\" encoding=\"UTF\u00AD8\"?>"
            + "<loginTicketRequest version=\"1.0\">  "
                + "<header>"
                    + "<source>cn=srv1,ou=facturacion,o=empresa s.a.,c=ar,serialNumber=CUIT 30123456789</source>"
                    + "<destination>cn=wsaa,o=afip,c=ar,serialNumber=CUIT 33693450239</destination>"
                    + "<uniqueId>4325399</uniqueId>    "
                    + "<generationTime>2001\u00AD12\u00AD31T12:00:00\u00AD03:00</generationTime>"
                    + "<expirationTime>2001\u00AD12\u00AD31T12:10:00\u00AD03:00</expirationTime>"
                + "</header>"
                + "<service>wsfe</service>"
            + "</loginTicketRequest>";

    @Test
    public void loginTicketRequestXML(){
        generateXML()
    }

    @Test
    public void build_keys(){
        buildKeys();

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
