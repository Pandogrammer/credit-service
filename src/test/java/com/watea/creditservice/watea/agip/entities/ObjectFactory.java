//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.11 at 10:49:05 AM ART 
//


package com.watea.creditservice.watea.agip.entities;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ar.com.watea.cot package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ar.com.watea.cot
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TBError }
     * 
     */
    public TBError createTBError() {
        return new TBError();
    }


    /**
     * Create an instance of {@link TBCOMPROBANTE }
     * 
     */
    public TBCOMPROBANTE createTBCOMPROBANTE() {
        return new TBCOMPROBANTE();
    }

    /**
     * Create an instance of {@link TBCOMPROBANTE.ValidacionesRemitos }
     * 
     */
    public TBCOMPROBANTE.ValidacionesRemitos createTBCOMPROBANTEValidacionesRemitos() {
        return new TBCOMPROBANTE.ValidacionesRemitos();
    }

    /**
     * Create an instance of {@link TBCOMPROBANTE.ValidacionesRemitos.Remito }
     * 
     */
    public TBCOMPROBANTE.ValidacionesRemitos.Remito createTBCOMPROBANTEValidacionesRemitosRemito() {
        return new TBCOMPROBANTE.ValidacionesRemitos.Remito();
    }

    /**
     * Create an instance of {@link TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores }
     * 
     */
    public TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores createTBCOMPROBANTEValidacionesRemitosRemitoErrores() {
        return new TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores();
    }

    /**
     * Create an instance of {@link TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error }
     * 
     */
    public TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error createTBCOMPROBANTEValidacionesRemitosRemitoErroresError() {
        return new TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error();
    }

}