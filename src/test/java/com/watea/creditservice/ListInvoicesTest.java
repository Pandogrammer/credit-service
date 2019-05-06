package com.watea.creditservice;

import org.junit.Before;
import org.junit.Test;
import wsfecred.afip.gob.ar.fecredservice.AuthRequestType;
import wsfecred.afip.gob.ar.fecredservice.ConsultarComprobanteRequestType;
import wsfecred.afip.gob.ar.fecredservice.FECredService;
import wsfecred.afip.gob.ar.fecredservice.FECredServicePortType;

public class ListInvoicesTest {

    private FECredServicePortType webService;
    private ConsultarComprobanteRequestType consultarComprobantes;
    private AuthRequestType authRequest;

    @Before
    public void before(){
        givenAWebService();
    }

    @Test
    public void nothing(){
        givenAuthRequest();
        givenConsultarComprobantesRequestWithAuth();

        whenConsultarComprobantes();
    }

    private void givenConsultarComprobantesRequestWithAuth() {
        consultarComprobantes = new ConsultarComprobanteRequestType();

        consultarComprobantes.setAuthRequest(authRequest);
    }

    private void givenAuthRequest() {
        authRequest = new AuthRequestType();
        authRequest.setToken("caca");
        authRequest.setSign("cacona");
    }

    private void givenAWebService() {
        webService = new FECredService().getFECredServiceSOAP();
    }

    private void whenConsultarComprobantes() {
        webService.consultarComprobantes(consultarComprobantes);
    }

}
