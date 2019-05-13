/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip;

import java.util.Properties;
import java.util.logging.Logger;

public class AGIPService {

    private Properties properties;
    private WSLoginManager wsLoginManager;
    private Logger log = Logger.getLogger(this.getClass().getName());

    public AGIPService() {
        try {
            log.entering(this.getClass().getName(), "constructor");
            
            properties = COTRequestProcess.getProperties();

            String crt = properties.getProperty("crt");
            String key = properties.getProperty("key");
            String cuit = properties.getProperty("cuit");
            String destination = properties.getProperty("destination");

            wsLoginManager = new WSLoginManager(crt, key, cuit, destination);
            
            log.exiting(this.getClass().getName(), "constructor");
        } catch (Exception ex) {
            log.throwing(this.getClass().getName(), "constructor", ex);
        }

    }

    public Credential getCredential() throws Exception {
        log.entering(this.getClass().getName(), "getCredential");

        String service = properties.getProperty("service");
        Credential credential = wsLoginManager.getCredential(service);
        
        log.exiting(this.getClass().getName(), "getCredential");
        return credential;
    }

}
