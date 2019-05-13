/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.services;

import com.watea.creditservice.watea.agip.entities.TBCOMPROBANTE;
import com.watea.creditservice.watea.agip.entities.TBError;

public class COTResponse {

    private TBCOMPROBANTE comprobante;
    private TBError error;

    public COTResponse() {
    }

    public TBCOMPROBANTE getComprobante() {
        return comprobante;
    }

    public void setComprobante(TBCOMPROBANTE comprobante) {
        this.comprobante = comprobante;
    }

    public TBError getError() {
        return error;
    }

    public void setError(TBError error) {
        this.error = error;
    }

    public TBCOMPROBANTE.ValidacionesRemitos.Remito getRemito() {
        if (comprobante != null) {
            return comprobante.getValidacionesRemitos().getRemito();
        } else {
            return null;
        }
    }

}
