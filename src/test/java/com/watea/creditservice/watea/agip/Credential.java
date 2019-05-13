/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip;

import ar.gov.agip.cc.controller.soap.LoginTicketResponse;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Representa la credencial generada por el webservice de autenticación de la
 * AFIP.
 *
 * @author Jorge Argibay(jorge.argibay@watea.com.ar)
 * @author Rodrigo Lemos(rodrigo.lemos@watea.com.ar)
 * @version 1.0
 * @since 1.0
 */
public class Credential {

    Logger log = Logger.getLogger(this.getClass().getName());

    private String token;
    private String signature;
    private LoginTicketResponse wsResp;

    /**
     * Inicializa la clase con los parámetros de la Unidad Operativa.
     *
     * @param wsresponse Este es el xml devuelto por el webservice de
     * autenticación de la AGIP.
     *
     * @since 1.0
     *
     */
    public Credential(LoginTicketResponse wsresponse) throws Exception {
        try {
            this.wsResp = wsresponse;
            this.signature = wsresponse.getCredentials().getSign();
            this.token = wsresponse.getCredentials().getToken();

        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "Constructor", e);
            throw (new Exception("No se pudo inicializar la credencial", e));
        }
    }

    /**
     * Metodo getSignature
     *
     * @return la firma digital generada por la AFIP.
     * @since 1.0
     *
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Metodo getToken
     *
     * @return El token de autenticación a ser utilizado en el webservice de
     * negocio.
     * @since 1.0
     *
     */
    public String getToken() {
        return token;
    }

    /**
     * Devuelve true si la fecha actual esta entre la fecha de generación y la
     * fecha de expira. Si no devuelve false.
     *
     * @return boolean True si la credencial.
     * @since 1.0
     *
     */
    public boolean isExpired() throws Exception {
        log.entering(this.getClass().getName(), "isExpired");
        try {

            XMLGregorianCalendar XMLExpTimeGreg = this.wsResp.getHeader().getExpirationTime();
            log.finest("[Expiration Date] " + XMLExpTimeGreg.toString());

            GregorianCalendar actualtime = new GregorianCalendar();
            actualtime.setTime(new Date());
            log.finest("[Actual time] " + actualtime.getTime().toString());
            log.finest("[Expiration time] " + XMLExpTimeGreg.toGregorianCalendar().getTime().toString());

            if (actualtime.getTime().compareTo(XMLExpTimeGreg.toGregorianCalendar().getTime()) > 0) {
                log.finest("[isExpired?] Si");
                return (true);
            } else {
                log.finest("[isExpired?] No");
                return (false);
            }
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "isExpired", e);
            throw (new Exception("Problema al verificar la validez de la credencial", e));
        }
    }
}
