package com.watea.creditservice;

import https.wsaahomo_afip_gov_ar.ws.services.logincms.LoginCMS;
import https.wsaahomo_afip_gov_ar.ws.services.logincms.LoginCMSService;
import org.junit.Test;

public class AfipLoginTest {

    @Test
    public void nothing(){
        LoginCMS login = new LoginCMSService().getLoginCms();


    }
}
