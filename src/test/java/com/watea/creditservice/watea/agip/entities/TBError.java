/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoError" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mensajeError" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tipoError",
    "codigoError",
    "mensajeError"
})
@XmlRootElement(name = "TBError")
public class TBError {

    @XmlElement(required = true)
    protected String tipoError;
    protected int codigoError;
    @XmlElement(required = true)
    protected String mensajeError;

    /**
     * Gets the value of the tipoError property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoError() {
        return tipoError;
    }

    /**
     * Sets the value of the tipoError property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoError(String value) {
        this.tipoError = value;
    }

    /**
     * Gets the value of the codigoError property.
     *
     */
    public int getCodigoError() {
        return codigoError;
    }

    /**
     * Sets the value of the codigoError property.
     *
     */
    public void setCodigoError(int value) {
        this.codigoError = value;
    }

    /**
     * Gets the value of the mensajeError property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * Sets the value of the mensajeError property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMensajeError(String value) {
        this.mensajeError = value;
    }

}
