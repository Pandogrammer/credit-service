/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "XXW_COT", catalog = "", schema = "BOLINF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "XxwCot.findAll", query = "SELECT x FROM XxwCot x")
    ,
    @NamedQuery(name = "XxwCot.findByLineId", query = "SELECT x FROM XxwCot x WHERE x.lineId = :lineId")
    ,
    @NamedQuery(name = "XxwCot.findByRemitoId", query = "SELECT x FROM XxwCot x WHERE x.remitoId = :remitoId")
    ,
    @NamedQuery(name = "XxwCot.findByTipoRegistro", query = "SELECT x FROM XxwCot x WHERE x.tipoRegistro = :tipoRegistro")
    ,
    @NamedQuery(name = "XxwCot.findByCuitEmpresa", query = "SELECT x FROM XxwCot x WHERE x.cuitEmpresa = :cuitEmpresa")
    ,
    @NamedQuery(name = "XxwCot.findByFechaEmision", query = "SELECT x FROM XxwCot x WHERE x.fechaEmision = :fechaEmision")
    ,
    @NamedQuery(name = "XxwCot.findByCodigoUnico", query = "SELECT x FROM XxwCot x WHERE x.codigoUnico = :codigoUnico")
    ,
    @NamedQuery(name = "XxwCot.findByFechaSalidaTransporte", query = "SELECT x FROM XxwCot x WHERE x.fechaSalidaTransporte = :fechaSalidaTransporte")
    ,
    @NamedQuery(name = "XxwCot.findByHoraSalidaTransporte", query = "SELECT x FROM XxwCot x WHERE x.horaSalidaTransporte = :horaSalidaTransporte")
    ,
    @NamedQuery(name = "XxwCot.findBySujetoGenerador", query = "SELECT x FROM XxwCot x WHERE x.sujetoGenerador = :sujetoGenerador")
    ,
    @NamedQuery(name = "XxwCot.findByDestinatarioConsumidorFinal", query = "SELECT x FROM XxwCot x WHERE x.destinatarioConsumidorFinal = :destinatarioConsumidorFinal")
    ,
    @NamedQuery(name = "XxwCot.findByDestinatarioTipoDocumento", query = "SELECT x FROM XxwCot x WHERE x.destinatarioTipoDocumento = :destinatarioTipoDocumento")
    ,
    @NamedQuery(name = "XxwCot.findByDestinatarioDocumento", query = "SELECT x FROM XxwCot x WHERE x.destinatarioDocumento = :destinatarioDocumento")
    ,
    @NamedQuery(name = "XxwCot.findByDestinatarioCuit", query = "SELECT x FROM XxwCot x WHERE x.destinatarioCuit = :destinatarioCuit")
    ,
    @NamedQuery(name = "XxwCot.findByDestinatarioRazonSocial", query = "SELECT x FROM XxwCot x WHERE x.destinatarioRazonSocial = :destinatarioRazonSocial")
    ,
    @NamedQuery(name = "XxwCot.findByDestinatarioTenedor", query = "SELECT x FROM XxwCot x WHERE x.destinatarioTenedor = :destinatarioTenedor")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioNumbero", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioNumbero = :destinoDomicilioNumbero")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioComple", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioComple = :destinoDomicilioComple")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioPiso", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioPiso = :destinoDomicilioPiso")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomilicioDto", query = "SELECT x FROM XxwCot x WHERE x.destinoDomilicioDto = :destinoDomilicioDto")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioBarrio", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioBarrio = :destinoDomicilioBarrio")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioCodpostal", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioCodpostal = :destinoDomicilioCodpostal")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioLocalidad", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioLocalidad = :destinoDomicilioLocalidad")
    ,
    @NamedQuery(name = "XxwCot.findByDestinoDomicilioProvincia", query = "SELECT x FROM XxwCot x WHERE x.destinoDomicilioProvincia = :destinoDomicilioProvincia")
    ,
    @NamedQuery(name = "XxwCot.findByPropioDestinoDomicilioCod", query = "SELECT x FROM XxwCot x WHERE x.propioDestinoDomicilioCod = :propioDestinoDomicilioCod")
    ,
    @NamedQuery(name = "XxwCot.findByEntregaDomicilioOrigen", query = "SELECT x FROM XxwCot x WHERE x.entregaDomicilioOrigen = :entregaDomicilioOrigen")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenCuit", query = "SELECT x FROM XxwCot x WHERE x.origenCuit = :origenCuit")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenRazonSocial", query = "SELECT x FROM XxwCot x WHERE x.origenRazonSocial = :origenRazonSocial")
    ,
    @NamedQuery(name = "XxwCot.findByEmisorTenedor", query = "SELECT x FROM XxwCot x WHERE x.emisorTenedor = :emisorTenedor")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioCalle", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioCalle = :origenDomicilioCalle")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioNumero", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioNumero = :origenDomicilioNumero")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioComple", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioComple = :origenDomicilioComple")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioPiso", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioPiso = :origenDomicilioPiso")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioDto", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioDto = :origenDomicilioDto")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioBarrio", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioBarrio = :origenDomicilioBarrio")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioCodpostal", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioCodpostal = :origenDomicilioCodpostal")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioLocalidad", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioLocalidad = :origenDomicilioLocalidad")
    ,
    @NamedQuery(name = "XxwCot.findByOrigenDomicilioProvincia", query = "SELECT x FROM XxwCot x WHERE x.origenDomicilioProvincia = :origenDomicilioProvincia")
    ,
    @NamedQuery(name = "XxwCot.findByTransportistaCuit", query = "SELECT x FROM XxwCot x WHERE x.transportistaCuit = :transportistaCuit")
    ,
    @NamedQuery(name = "XxwCot.findByTipoRecorrido", query = "SELECT x FROM XxwCot x WHERE x.tipoRecorrido = :tipoRecorrido")
    ,
    @NamedQuery(name = "XxwCot.findByRecorridoLocalidad", query = "SELECT x FROM XxwCot x WHERE x.recorridoLocalidad = :recorridoLocalidad")
    ,
    @NamedQuery(name = "XxwCot.findByRecorridoCalle", query = "SELECT x FROM XxwCot x WHERE x.recorridoCalle = :recorridoCalle")
    ,
    @NamedQuery(name = "XxwCot.findByRecorridoRuta", query = "SELECT x FROM XxwCot x WHERE x.recorridoRuta = :recorridoRuta")
    ,
    @NamedQuery(name = "XxwCot.findByPatenteVehiculo", query = "SELECT x FROM XxwCot x WHERE x.patenteVehiculo = :patenteVehiculo")
    ,
    @NamedQuery(name = "XxwCot.findByPatenteAcoplado", query = "SELECT x FROM XxwCot x WHERE x.patenteAcoplado = :patenteAcoplado")
    ,
    @NamedQuery(name = "XxwCot.findByProductoNoTermDev", query = "SELECT x FROM XxwCot x WHERE x.productoNoTermDev = :productoNoTermDev")
    ,
    @NamedQuery(name = "XxwCot.findByImporte", query = "SELECT x FROM XxwCot x WHERE x.importe = :importe")
    ,
    @NamedQuery(name = "XxwCot.findByCodigoUnicoProducto", query = "SELECT x FROM XxwCot x WHERE x.codigoUnicoProducto = :codigoUnicoProducto")
    ,
    @NamedQuery(name = "XxwCot.findByRentasCodigoUmed", query = "SELECT x FROM XxwCot x WHERE x.rentasCodigoUmed = :rentasCodigoUmed")
    ,
    @NamedQuery(name = "XxwCot.findByCantidad", query = "SELECT x FROM XxwCot x WHERE x.cantidad = :cantidad")
    ,
    @NamedQuery(name = "XxwCot.findByPropioCodigoProducto", query = "SELECT x FROM XxwCot x WHERE x.propioCodigoProducto = :propioCodigoProducto")
    ,
    @NamedQuery(name = "XxwCot.findByPropioDescripcionProd", query = "SELECT x FROM XxwCot x WHERE x.propioDescripcionProd = :propioDescripcionProd")
    ,
    @NamedQuery(name = "XxwCot.findByPropioDescripcionUmed", query = "SELECT x FROM XxwCot x WHERE x.propioDescripcionUmed = :propioDescripcionUmed")
    ,
    @NamedQuery(name = "XxwCot.findByCantidadAjustada", query = "SELECT x FROM XxwCot x WHERE x.cantidadAjustada = :cantidadAjustada")
    ,
    @NamedQuery(name = "XxwCot.findByCantidadTotalRemitos", query = "SELECT x FROM XxwCot x WHERE x.cantidadTotalRemitos = :cantidadTotalRemitos")
    ,
    @NamedQuery(name = "XxwCot.findByLastUpdateDate", query = "SELECT x FROM XxwCot x WHERE x.lastUpdateDate = :lastUpdateDate")
    ,
    @NamedQuery(name = "XxwCot.findByLastUpdatedBy", query = "SELECT x FROM XxwCot x WHERE x.lastUpdatedBy = :lastUpdatedBy")
    ,
    @NamedQuery(name = "XxwCot.findByCreationDate", query = "SELECT x FROM XxwCot x WHERE x.creationDate = :creationDate")
    ,
    @NamedQuery(name = "XxwCot.findByCreatedBy", query = "SELECT x FROM XxwCot x WHERE x.createdBy = :createdBy")
    ,
    @NamedQuery(name = "XxwCot.findByProcesado", query = "SELECT x FROM XxwCot x WHERE x.procesado = :procesado")
    ,
    @NamedQuery(name = "XxwCot.findByStatus", query = "SELECT x FROM XxwCot x WHERE x.status = :status")
    ,
    @NamedQuery(name = "XxwCot.findByMessage", query = "SELECT x FROM XxwCot x WHERE x.message = :message")})
public class XxwCot implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "LINE_ID")
    private BigDecimal lineId;
    @Column(name = "REMITO_ID")
    private BigInteger remitoId;
    @Column(name = "TIPO_REGISTRO")
    private String tipoRegistro;
    @Column(name = "CUIT_EMPRESA")
    private String cuitEmpresa;
    @Column(name = "FECHA_EMISION")
    private String fechaEmision;
    @Column(name = "CODIGO_UNICO")
    private String codigoUnico;
    @Column(name = "FECHA_SALIDA_TRANSPORTE")
    private String fechaSalidaTransporte;
    @Column(name = "HORA_SALIDA_TRANSPORTE")
    private String horaSalidaTransporte;
    @Column(name = "SUJETO_GENERADOR")
    private String sujetoGenerador;
    @Column(name = "DESTINATARIO_CONSUMIDOR_FINAL")
    private BigInteger destinatarioConsumidorFinal;
    @Column(name = "DESTINATARIO_TIPO_DOCUMENTO")
    private String destinatarioTipoDocumento;
    @Column(name = "DESTINATARIO_DOCUMENTO")
    private String destinatarioDocumento;
    @Column(name = "DESTINATARIO_CUIT")
    private String destinatarioCuit;
    @Column(name = "DESTINATARIO_RAZON_SOCIAL")
    private String destinatarioRazonSocial;
    @Column(name = "DESTINATARIO_TENEDOR")
    private BigInteger destinatarioTenedor;
    @Column(name = "DESTINO_DOMICILIO_NUMBERO")
    private BigInteger destinoDomicilioNumbero;
    @Column(name = "DESTINO_DOMICILIO_COMPLE")
    private String destinoDomicilioComple;
    @Column(name = "DESTINO_DOMICILIO_PISO")
    private String destinoDomicilioPiso;
    @Column(name = "DESTINO_DOMILICIO_DTO")
    private String destinoDomilicioDto;
    @Column(name = "DESTINO_DOMICILIO_BARRIO")
    private String destinoDomicilioBarrio;
    @Column(name = "DESTINO_DOMICILIO_CODPOSTAL")
    private String destinoDomicilioCodpostal;
    @Column(name = "DESTINO_DOMICILIO_LOCALIDAD")
    private String destinoDomicilioLocalidad;
    @Column(name = "DESTINO_DOMICILIO_PROVINCIA")
    private String destinoDomicilioProvincia;
    @Column(name = "PROPIO_DESTINO_DOMICILIO_COD")
    private String propioDestinoDomicilioCod;
    @Column(name = "ENTREGA_DOMICILIO_ORIGEN")
    private String entregaDomicilioOrigen;
    @Column(name = "ORIGEN_CUIT")
    private BigInteger origenCuit;
    @Column(name = "ORIGEN_RAZON_SOCIAL")
    private String origenRazonSocial;
    @Column(name = "EMISOR_TENEDOR")
    private BigInteger emisorTenedor;
    @Column(name = "ORIGEN_DOMICILIO_CALLE")
    private String origenDomicilioCalle;
    @Column(name = "ORIGEN_DOMICILIO_NUMERO")
    private BigInteger origenDomicilioNumero;
    @Column(name = "ORIGEN_DOMICILIO_COMPLE")
    private String origenDomicilioComple;
    @Column(name = "ORIGEN_DOMICILIO_PISO")
    private String origenDomicilioPiso;
    @Column(name = "ORIGEN_DOMICILIO_DTO")
    private String origenDomicilioDto;
    @Column(name = "ORIGEN_DOMICILIO_BARRIO")
    private String origenDomicilioBarrio;
    @Column(name = "ORIGEN_DOMICILIO_CODPOSTAL")
    private String origenDomicilioCodpostal;
    @Column(name = "ORIGEN_DOMICILIO_LOCALIDAD")
    private String origenDomicilioLocalidad;
    @Column(name = "ORIGEN_DOMICILIO_PROVINCIA")
    private String origenDomicilioProvincia;
    @Column(name = "TRANSPORTISTA_CUIT")
    private BigInteger transportistaCuit;
    @Column(name = "TIPO_RECORRIDO")
    private String tipoRecorrido;
    @Column(name = "RECORRIDO_LOCALIDAD")
    private String recorridoLocalidad;
    @Column(name = "RECORRIDO_CALLE")
    private String recorridoCalle;
    @Column(name = "RECORRIDO_RUTA")
    private String recorridoRuta;
    @Column(name = "PATENTE_VEHICULO")
    private String patenteVehiculo;
    @Column(name = "PATENTE_ACOPLADO")
    private String patenteAcoplado;
    @Column(name = "PRODUCTO_NO_TERM_DEV")
    private BigInteger productoNoTermDev;
    @Column(name = "IMPORTE")
    private BigInteger importe;
    @Column(name = "CODIGO_UNICO_PRODUCTO")
    private String codigoUnicoProducto;
    @Column(name = "RENTAS_CODIGO_UMED")
    private String rentasCodigoUmed;
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Column(name = "PROPIO_CODIGO_PRODUCTO")
    private String propioCodigoProducto;
    @Column(name = "PROPIO_DESCRIPCION_PROD")
    private String propioDescripcionProd;
    @Column(name = "PROPIO_DESCRIPCION_UMED")
    private String propioDescripcionUmed;
    @Column(name = "CANTIDAD_AJUSTADA")
    private BigInteger cantidadAjustada;
    @Column(name = "CANTIDAD_TOTAL_REMITOS")
    private BigInteger cantidadTotalRemitos;
    @Column(name = "LAST_UPDATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column(name = "LAST_UPDATED_BY")
    private BigInteger lastUpdatedBy;
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "CREATED_BY")
    private BigInteger createdBy;
    @Column(name = "PROCESADO")
    private String procesado;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "MESSAGE")
    private String message;

    public XxwCot() {
    }

    public XxwCot(BigDecimal lineId) {
        this.lineId = lineId;
    }

    public BigDecimal getLineId() {
        return lineId;
    }

    public void setLineId(BigDecimal lineId) {
        this.lineId = lineId;
    }

    public BigInteger getRemitoId() {
        return remitoId;
    }

    public void setRemitoId(BigInteger remitoId) {
        this.remitoId = remitoId;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getCuitEmpresa() {
        return cuitEmpresa;
    }

    public void setCuitEmpresa(String cuitEmpresa) {
        this.cuitEmpresa = cuitEmpresa;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getFechaSalidaTransporte() {
        return fechaSalidaTransporte;
    }

    public void setFechaSalidaTransporte(String fechaSalidaTransporte) {
        this.fechaSalidaTransporte = fechaSalidaTransporte;
    }

    public String getHoraSalidaTransporte() {
        return horaSalidaTransporte;
    }

    public void setHoraSalidaTransporte(String horaSalidaTransporte) {
        this.horaSalidaTransporte = horaSalidaTransporte;
    }

    public String getSujetoGenerador() {
        return sujetoGenerador;
    }

    public void setSujetoGenerador(String sujetoGenerador) {
        this.sujetoGenerador = sujetoGenerador;
    }

    public BigInteger getDestinatarioConsumidorFinal() {
        return destinatarioConsumidorFinal;
    }

    public void setDestinatarioConsumidorFinal(BigInteger destinatarioConsumidorFinal) {
        this.destinatarioConsumidorFinal = destinatarioConsumidorFinal;
    }

    public String getDestinatarioTipoDocumento() {
        return destinatarioTipoDocumento;
    }

    public void setDestinatarioTipoDocumento(String destinatarioTipoDocumento) {
        this.destinatarioTipoDocumento = destinatarioTipoDocumento;
    }

    public String getDestinatarioDocumento() {
        return destinatarioDocumento;
    }

    public void setDestinatarioDocumento(String destinatarioDocumento) {
        this.destinatarioDocumento = destinatarioDocumento;
    }

    public String getDestinatarioCuit() {
        return destinatarioCuit;
    }

    public void setDestinatarioCuit(String destinatarioCuit) {
        this.destinatarioCuit = destinatarioCuit;
    }

    public String getDestinatarioRazonSocial() {
        return destinatarioRazonSocial;
    }

    public void setDestinatarioRazonSocial(String destinatarioRazonSocial) {
        this.destinatarioRazonSocial = destinatarioRazonSocial;
    }

    public BigInteger getDestinatarioTenedor() {
        return destinatarioTenedor;
    }

    public void setDestinatarioTenedor(BigInteger destinatarioTenedor) {
        this.destinatarioTenedor = destinatarioTenedor;
    }

    public BigInteger getDestinoDomicilioNumbero() {
        return destinoDomicilioNumbero;
    }

    public void setDestinoDomicilioNumbero(BigInteger destinoDomicilioNumbero) {
        this.destinoDomicilioNumbero = destinoDomicilioNumbero;
    }

    public String getDestinoDomicilioComple() {
        return destinoDomicilioComple;
    }

    public void setDestinoDomicilioComple(String destinoDomicilioComple) {
        this.destinoDomicilioComple = destinoDomicilioComple;
    }

    public String getDestinoDomicilioPiso() {
        return destinoDomicilioPiso;
    }

    public void setDestinoDomicilioPiso(String destinoDomicilioPiso) {
        this.destinoDomicilioPiso = destinoDomicilioPiso;
    }

    public String getDestinoDomilicioDto() {
        return destinoDomilicioDto;
    }

    public void setDestinoDomilicioDto(String destinoDomilicioDto) {
        this.destinoDomilicioDto = destinoDomilicioDto;
    }

    public String getDestinoDomicilioBarrio() {
        return destinoDomicilioBarrio;
    }

    public void setDestinoDomicilioBarrio(String destinoDomicilioBarrio) {
        this.destinoDomicilioBarrio = destinoDomicilioBarrio;
    }

    public String getDestinoDomicilioCodpostal() {
        return destinoDomicilioCodpostal;
    }

    public void setDestinoDomicilioCodpostal(String destinoDomicilioCodpostal) {
        this.destinoDomicilioCodpostal = destinoDomicilioCodpostal;
    }

    public String getDestinoDomicilioLocalidad() {
        return destinoDomicilioLocalidad;
    }

    public void setDestinoDomicilioLocalidad(String destinoDomicilioLocalidad) {
        this.destinoDomicilioLocalidad = destinoDomicilioLocalidad;
    }

    public String getDestinoDomicilioProvincia() {
        return destinoDomicilioProvincia;
    }

    public void setDestinoDomicilioProvincia(String destinoDomicilioProvincia) {
        this.destinoDomicilioProvincia = destinoDomicilioProvincia;
    }

    public String getPropioDestinoDomicilioCod() {
        return propioDestinoDomicilioCod;
    }

    public void setPropioDestinoDomicilioCod(String propioDestinoDomicilioCod) {
        this.propioDestinoDomicilioCod = propioDestinoDomicilioCod;
    }

    public String getEntregaDomicilioOrigen() {
        return entregaDomicilioOrigen;
    }

    public void setEntregaDomicilioOrigen(String entregaDomicilioOrigen) {
        this.entregaDomicilioOrigen = entregaDomicilioOrigen;
    }

    public BigInteger getOrigenCuit() {
        return origenCuit;
    }

    public void setOrigenCuit(BigInteger origenCuit) {
        this.origenCuit = origenCuit;
    }

    public String getOrigenRazonSocial() {
        return origenRazonSocial;
    }

    public void setOrigenRazonSocial(String origenRazonSocial) {
        this.origenRazonSocial = origenRazonSocial;
    }

    public BigInteger getEmisorTenedor() {
        return emisorTenedor;
    }

    public void setEmisorTenedor(BigInteger emisorTenedor) {
        this.emisorTenedor = emisorTenedor;
    }

    public String getOrigenDomicilioCalle() {
        return origenDomicilioCalle;
    }

    public void setOrigenDomicilioCalle(String origenDomicilioCalle) {
        this.origenDomicilioCalle = origenDomicilioCalle;
    }

    public BigInteger getOrigenDomicilioNumero() {
        return origenDomicilioNumero;
    }

    public void setOrigenDomicilioNumero(BigInteger origenDomicilioNumero) {
        this.origenDomicilioNumero = origenDomicilioNumero;
    }

    public String getOrigenDomicilioComple() {
        return origenDomicilioComple;
    }

    public void setOrigenDomicilioComple(String origenDomicilioComple) {
        this.origenDomicilioComple = origenDomicilioComple;
    }

    public String getOrigenDomicilioPiso() {
        return origenDomicilioPiso;
    }

    public void setOrigenDomicilioPiso(String origenDomicilioPiso) {
        this.origenDomicilioPiso = origenDomicilioPiso;
    }

    public String getOrigenDomicilioDto() {
        return origenDomicilioDto;
    }

    public void setOrigenDomicilioDto(String origenDomicilioDto) {
        this.origenDomicilioDto = origenDomicilioDto;
    }

    public String getOrigenDomicilioBarrio() {
        return origenDomicilioBarrio;
    }

    public void setOrigenDomicilioBarrio(String origenDomicilioBarrio) {
        this.origenDomicilioBarrio = origenDomicilioBarrio;
    }

    public String getOrigenDomicilioCodpostal() {
        return origenDomicilioCodpostal;
    }

    public void setOrigenDomicilioCodpostal(String origenDomicilioCodpostal) {
        this.origenDomicilioCodpostal = origenDomicilioCodpostal;
    }

    public String getOrigenDomicilioLocalidad() {
        return origenDomicilioLocalidad;
    }

    public void setOrigenDomicilioLocalidad(String origenDomicilioLocalidad) {
        this.origenDomicilioLocalidad = origenDomicilioLocalidad;
    }

    public String getOrigenDomicilioProvincia() {
        return origenDomicilioProvincia;
    }

    public void setOrigenDomicilioProvincia(String origenDomicilioProvincia) {
        this.origenDomicilioProvincia = origenDomicilioProvincia;
    }

    public BigInteger getTransportistaCuit() {
        return transportistaCuit;
    }

    public void setTransportistaCuit(BigInteger transportistaCuit) {
        this.transportistaCuit = transportistaCuit;
    }

    public String getTipoRecorrido() {
        return tipoRecorrido;
    }

    public void setTipoRecorrido(String tipoRecorrido) {
        this.tipoRecorrido = tipoRecorrido;
    }

    public String getRecorridoLocalidad() {
        return recorridoLocalidad;
    }

    public void setRecorridoLocalidad(String recorridoLocalidad) {
        this.recorridoLocalidad = recorridoLocalidad;
    }

    public String getRecorridoCalle() {
        return recorridoCalle;
    }

    public void setRecorridoCalle(String recorridoCalle) {
        this.recorridoCalle = recorridoCalle;
    }

    public String getRecorridoRuta() {
        return recorridoRuta;
    }

    public void setRecorridoRuta(String recorridoRuta) {
        this.recorridoRuta = recorridoRuta;
    }

    public String getPatenteVehiculo() {
        return patenteVehiculo;
    }

    public void setPatenteVehiculo(String patenteVehiculo) {
        this.patenteVehiculo = patenteVehiculo;
    }

    public String getPatenteAcoplado() {
        return patenteAcoplado;
    }

    public void setPatenteAcoplado(String patenteAcoplado) {
        this.patenteAcoplado = patenteAcoplado;
    }

    public BigInteger getProductoNoTermDev() {
        return productoNoTermDev;
    }

    public void setProductoNoTermDev(BigInteger productoNoTermDev) {
        this.productoNoTermDev = productoNoTermDev;
    }

    public BigInteger getImporte() {
        return importe;
    }

    public void setImporte(BigInteger importe) {
        this.importe = importe;
    }

    public String getCodigoUnicoProducto() {
        return codigoUnicoProducto;
    }

    public void setCodigoUnicoProducto(String codigoUnicoProducto) {
        this.codigoUnicoProducto = codigoUnicoProducto;
    }

    public String getRentasCodigoUmed() {
        return rentasCodigoUmed;
    }

    public void setRentasCodigoUmed(String rentasCodigoUmed) {
        this.rentasCodigoUmed = rentasCodigoUmed;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getPropioCodigoProducto() {
        return propioCodigoProducto;
    }

    public void setPropioCodigoProducto(String propioCodigoProducto) {
        this.propioCodigoProducto = propioCodigoProducto;
    }

    public String getPropioDescripcionProd() {
        return propioDescripcionProd;
    }

    public void setPropioDescripcionProd(String propioDescripcionProd) {
        this.propioDescripcionProd = propioDescripcionProd;
    }

    public String getPropioDescripcionUmed() {
        return propioDescripcionUmed;
    }

    public void setPropioDescripcionUmed(String propioDescripcionUmed) {
        this.propioDescripcionUmed = propioDescripcionUmed;
    }

    public BigInteger getCantidadAjustada() {
        return cantidadAjustada;
    }

    public void setCantidadAjustada(BigInteger cantidadAjustada) {
        this.cantidadAjustada = cantidadAjustada;
    }

    public BigInteger getCantidadTotalRemitos() {
        return cantidadTotalRemitos;
    }

    public void setCantidadTotalRemitos(BigInteger cantidadTotalRemitos) {
        this.cantidadTotalRemitos = cantidadTotalRemitos;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public BigInteger getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(BigInteger lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineId != null ? lineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof XxwCot)) {
            return false;
        }
        XxwCot other = (XxwCot) object;
        if ((this.lineId == null && other.lineId != null) || (this.lineId != null && !this.lineId.equals(other.lineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.com.watea.agip.entities.XxwCot[ lineId=" + lineId + " ]";
    }

}
