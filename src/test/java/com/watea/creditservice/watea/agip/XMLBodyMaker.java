/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * Se ocupa de generar el cuerpo del XML utilizado para el servicio WSAA de la
 * AFIP
 *
 * @author Jorge Argibay(jorge.argibay@watea.com.ar)
 * @version 1.0
 * @since 1.0
 */
public class XMLBodyMaker // extends Thread
{

    /**
     * Class logger
     */
    Logger log = Logger.getLogger(this.getClass().getName());

    String signerDN;
    String dstDN;
    String service;
    Long TicketTime = new Long(3600000);

    /**
     * Inicializa la clase con los parámetros de la Unidad Operativa.
     *
     * @param empresaDN DN de la empresa.
     * @param destinationDN DN del destino.
     * @param serviceName Nombre del servicio.
     *
     * @since 1.0
    *
     */
    public XMLBodyMaker(String empresaDN, String destinationDN, String serviceName) {
        log.entering(this.getClass().getName(), "Constructor");

        this.signerDN = empresaDN;
        this.dstDN = destinationDN;
        this.service = serviceName;

        log.exiting(this.getClass().getName(), "Constructor");
    }

    /**
     * Devuelve el XML a firmar, con el nombre de la empresa y el CUIT.
     *
     * @return Lo que sea que devuelve
     * @since 1.0
    *
     */
    public byte[] getData() {

        log.entering(this.getClass().getName(), "loadKeyStore");
        String loginTicketRequest_xml;

        Date GenTime = new Date();
        GregorianCalendar gentime = new GregorianCalendar();
        GregorianCalendar exptime = new GregorianCalendar();
        String UniqueId = new Long(GenTime.getTime() / 1000).toString();

        exptime.setTime(new Date(GenTime.getTime() + TicketTime));

        XMLGregorianCalendarImpl XMLGenTime = new XMLGregorianCalendarImpl(gentime);
        XMLGregorianCalendarImpl XMLExpTime = new XMLGregorianCalendarImpl(exptime);

        loginTicketRequest_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<loginTicketRequest version=\"1.0\">"
                + "<header>"
                + "<source>" + signerDN + "</source>"
                + "<destination>" + dstDN + "</destination>"
                + "<uniqueId>" + UniqueId + "</uniqueId>"
                + "<generationTime>" + XMLGenTime + "</generationTime>"
                + "<expirationTime>" + XMLExpTime + "</expirationTime>"
                + "</header>"
                + "<service>" + service + "</service>"
                + "</loginTicketRequest>";

        log.exiting(this.getClass().getName(), "getData", loginTicketRequest_xml);
        return (loginTicketRequest_xml.getBytes());
    }
}
