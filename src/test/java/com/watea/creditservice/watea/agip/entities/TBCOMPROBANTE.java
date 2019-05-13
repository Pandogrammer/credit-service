/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.entities;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="cuitEmpresa" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="numeroComprobante" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombreArchivo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoIntegridad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="validacionesRemitos">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="remito">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="numeroUnico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="procesado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="errores">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="error">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                                 &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "cuitEmpresa",
    "numeroComprobante",
    "nombreArchivo",
    "codigoIntegridad",
    "validacionesRemitos"
})
@XmlRootElement(name = "TBCOMPROBANTE")
public class TBCOMPROBANTE {

    @XmlElement(required = true)
    protected BigDecimal cuitEmpresa;
    protected int numeroComprobante;
    @XmlElement(required = true)
    protected String nombreArchivo;
    @XmlElement(required = true)
    protected String codigoIntegridad;
    @XmlElement(required = true)
    protected TBCOMPROBANTE.ValidacionesRemitos validacionesRemitos;

    /**
     * Gets the value of the cuitEmpresa property.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getCuitEmpresa() {
        return cuitEmpresa;
    }

    /**
     * Sets the value of the cuitEmpresa property.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setCuitEmpresa(BigDecimal value) {
        this.cuitEmpresa = value;
    }

    /**
     * Gets the value of the numeroComprobante property.
     *
     */
    public int getNumeroComprobante() {
        return numeroComprobante;
    }

    /**
     * Sets the value of the numeroComprobante property.
     *
     */
    public void setNumeroComprobante(int value) {
        this.numeroComprobante = value;
    }

    /**
     * Gets the value of the nombreArchivo property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Sets the value of the nombreArchivo property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNombreArchivo(String value) {
        this.nombreArchivo = value;
    }

    /**
     * Gets the value of the codigoIntegridad property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodigoIntegridad() {
        return codigoIntegridad;
    }

    /**
     * Sets the value of the codigoIntegridad property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodigoIntegridad(String value) {
        this.codigoIntegridad = value;
    }

    /**
     * Gets the value of the validacionesRemitos property.
     *
     * @return possible object is {@link TBCOMPROBANTE.ValidacionesRemitos }
     *
     */
    public TBCOMPROBANTE.ValidacionesRemitos getValidacionesRemitos() {
        return validacionesRemitos;
    }

    /**
     * Sets the value of the validacionesRemitos property.
     *
     * @param value allowed object is {@link TBCOMPROBANTE.ValidacionesRemitos }
     *
     */
    public void setValidacionesRemitos(TBCOMPROBANTE.ValidacionesRemitos value) {
        this.validacionesRemitos = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     *
     * <p>
     * The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="remito">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="numeroUnico" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="procesado" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="errores">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="error">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                                       &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "remito"
    })
    public static class ValidacionesRemitos {

        @XmlElement(required = true)
        protected TBCOMPROBANTE.ValidacionesRemitos.Remito remito;
        @XmlAttribute(name = "class")
        protected String clazz;

        /**
         * Gets the value of the remito property.
         *
         * @return possible object is
         *     {@link TBCOMPROBANTE.ValidacionesRemitos.Remito }
         *
         */
        public TBCOMPROBANTE.ValidacionesRemitos.Remito getRemito() {
            return remito;
        }

        /**
         * Sets the value of the remito property.
         *
         * @param value allowed object is
         *     {@link TBCOMPROBANTE.ValidacionesRemitos.Remito }
         *
         */
        public void setRemito(TBCOMPROBANTE.ValidacionesRemitos.Remito value) {
            this.remito = value;
        }

        /**
         * Gets the value of the clazz property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getClazz() {
            return clazz;
        }

        /**
         * Sets the value of the clazz property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setClazz(String value) {
            this.clazz = value;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         *
         * <p>
         * The following schema fragment specifies the expected content
         * contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="numeroUnico" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="procesado" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="errores">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="error">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                             &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "numeroUnico",
            "procesado",
            "errores"
        })
        public static class Remito {

            @XmlElement(required = true)
            protected String numeroUnico;
            @XmlElement(required = true)
            protected String procesado;
            @XmlElement(required = true)
            protected TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores errores;

            /**
             * Gets the value of the numeroUnico property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getNumeroUnico() {
                return numeroUnico;
            }

            /**
             * Sets the value of the numeroUnico property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setNumeroUnico(String value) {
                this.numeroUnico = value;
            }

            /**
             * Gets the value of the procesado property.
             *
             * @return possible object is {@link String }
             *
             */
            public String getProcesado() {
                return procesado;
            }

            /**
             * Sets the value of the procesado property.
             *
             * @param value allowed object is {@link String }
             *
             */
            public void setProcesado(String value) {
                this.procesado = value;
            }

            /**
             * Gets the value of the errores property.
             *
             * @return possible object is
             *     {@link TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores }
             *
             */
            public TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores getErrores() {
                return errores;
            }

            /**
             * Sets the value of the errores property.
             *
             * @param value allowed object is
             *     {@link TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores }
             *
             */
            public void setErrores(TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores value) {
                this.errores = value;
            }

            /**
             * <p>
             * Java class for anonymous complex type.
             *
             * <p>
             * The following schema fragment specifies the expected content
             * contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="error">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *                   &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "error"
            })
            public static class Errores {

                @XmlElement(required = true)
                protected TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error error;
                @XmlAttribute(name = "class")
                protected String clazz;

                /**
                 * Gets the value of the error property.
                 *
                 * @return possible object is
                 *     {@link TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error }
                 *
                 */
                public TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error getError() {
                    return error;
                }

                /**
                 * Sets the value of the error property.
                 *
                 * @param value allowed object is
                 *     {@link TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error }
                 *
                 */
                public void setError(TBCOMPROBANTE.ValidacionesRemitos.Remito.Errores.Error value) {
                    this.error = value;
                }

                /**
                 * Gets the value of the clazz property.
                 *
                 * @return possible object is {@link String }
                 *
                 */
                public String getClazz() {
                    return clazz;
                }

                /**
                 * Sets the value of the clazz property.
                 *
                 * @param value allowed object is {@link String }
                 *
                 */
                public void setClazz(String value) {
                    this.clazz = value;
                }

                /**
                 * <p>
                 * Java class for anonymous complex type.
                 *
                 * <p>
                 * The following schema fragment specifies the expected content
                 * contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
                 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "codigo",
                    "descripcion"
                })
                public static class Error {

                    protected int codigo;
                    @XmlElement(required = true)
                    protected String descripcion;

                    /**
                     * Gets the value of the codigo property.
                     *
                     */
                    public int getCodigo() {
                        return codigo;
                    }

                    /**
                     * Sets the value of the codigo property.
                     *
                     */
                    public void setCodigo(int value) {
                        this.codigo = value;
                    }

                    /**
                     * Gets the value of the descripcion property.
                     *
                     * @return possible object is {@link String }
                     *
                     */
                    public String getDescripcion() {
                        return descripcion;
                    }

                    /**
                     * Sets the value of the descripcion property.
                     *
                     * @param value allowed object is {@link String }
                     *
                     */
                    public void setDescripcion(String value) {
                        this.descripcion = value;
                    }

                }

            }

        }

    }

}
