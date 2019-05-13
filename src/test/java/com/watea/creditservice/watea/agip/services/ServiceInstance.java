/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.*;

import com.watea.creditservice.watea.agip.AGIPService;
import com.watea.creditservice.watea.agip.controller.ArbaConnectionController;
import com.watea.creditservice.watea.agip.controller.XxwCotJpaController;
import com.watea.creditservice.watea.agip.entities.TBCOMPROBANTE;
import com.watea.creditservice.watea.agip.entities.XxwCot;

import oracle.jms.AQjmsQueueConnectionFactory;
import oracle.jms.AQjmsSession;

public class ServiceInstance implements Runnable {

    private Logger log = Logger.getLogger(this.getClass().getName());
    private ServiceManager parentService;
    private COTService cotService;
    private XxwCotJpaController cotController;
    private ArbaConnectionController arbaConnectionController;
    private AGIPService agipService;

    private QueueConnection qc = null;
    private QueueSession qs = null;
    private Queue q = null;
    private MessageConsumer mc = null;

    private Connection DBConn = null;

    private TextMessage currentMessage;

    /**
     * Crea un Service Instance. Responsabilidades:
     * <ul>
     * <li>Adaptar la conección JDBC para que se pueda usar para leer una cola
     * de AQ via JMS.</li>
     * </ul>
     *
     * @since 1.0
     */
    public ServiceInstance(ServiceManager parent) throws Exception {

        try {
            log.info("ServiceInstance constructor");
            log.entering(this.getClass().getName(), "ServiceInstance Constructor");

            this.parentService = parent;
            this.cotService = new COTService();
            this.cotController = new XxwCotJpaController(parent.getEntityManagerFactory());
            this.arbaConnectionController = new ArbaConnectionController(parent.getEntityManagerFactory());
            this.agipService = new AGIPService();

            try {
                this.cotService.setToken(agipService.getCredential().getToken());
                this.cotService.setSign(agipService.getCredential().getSignature());
                this.cotService.setServiceURL(arbaConnectionController.getArbaService());
            } catch (Exception e) {
                throw new Exception("No se pudo setear la conexion a arba. Causa: "+e.getMessage());
            }

            createConsumer();

            log.exiting(this.getClass().getName(), "ServiceInstance Constructor");
        } catch (Exception e) {
            try {
                if (DBConn != null) {
                    DBConn.close();
                }
                DBConn = null;
                log.finest("Cerrando la conexion");
                //log.throwing(this.getClass().getName(), "Constructor", e);
                log.info("Constructor: " + e.getMessage());
                log.log(Level.SEVERE, this.getClass().getName() + "Constructor", e);
                log.exiting(this.getClass().getName(), "Constructor");
            } catch (Exception ex) {
                try {
                    if (DBConn != null) {
                        DBConn.close();
                    }
                    DBConn = null;
                } catch (Exception exc) {
                    log.info("No se pudo cerrar correctamente la conexion con la base de datos");
                }
                //log.throwing (this.getClass().getName(), "Error al cerrar la conexión con la base de datos", ex);
                log.info("Error al cerrar la conexión con la base de datos: " + ex.getMessage());
            }

            throw (e);
        }
    }

    public void run() {
        log.entering(this.getClass().getName(), "procesar");

        while (this.parentService.isRunning()) {
            try {
                BigDecimal lineId = this.getLineId();
                COTResponse cotResponse = new COTResponse();
                String logFile;
                if (lineId != null) {

                    log.info("Tenemos trabajo. line_id: " + lineId.toString());
                    this.ackMessage();
                    try {
                        //System.setOut(new PrintStream(currentMessage.getStringProperty("LogFile")));
                        //System.out.println("Log file seteado.");
                        cotResponse = cotService.processFile(currentMessage.getStringProperty("ProcessFile"),
                                currentMessage.getStringProperty("FileName"));
                    } catch (Exception e) {
                        System.out.println("Error al procesar el archivo: " + e.getMessage());
                        log.info("Error al procesar el archivo: " + e.getMessage());
                    }

                    if (cotResponse != null) {
                        try {
                            saveResponse(lineId, cotResponse);
                        } catch (Exception e) {
                            System.out.println("Error al guardar la respuesta en la base de datos: " + e.getMessage());
                            //log.throwing (this.getClass().getName(), "Error al guardar la respuesta en la base de datos", e);
                            log.info("Error al guardar la respuesta en la base de datos: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Error del sistema");
                    }

                }
            } catch (Exception e) {

            }
        }
        try {
            log.finest("Cerrando la coneccion");
            if (DBConn != null) {
                DBConn.close();
            }
            DBConn = null;
        } catch (Exception ex) {
            //log.throwing (this.getClass().getName(), "Error al cerrar la conexión con la base de datos", ex);
            log.info("Error al cerrar la conexión con la base de datos: " + ex.getMessage());
        }
        log.exiting(this.getClass().getName(), "procesarFacturas");
    }

    private void saveResponse(BigDecimal lineId, COTResponse response) throws Exception {
        log.info("Guardando respuesta para: " + lineId);
        XxwCot cotLine = cotController.findXxwCot(lineId);
        
        log.info("Se encontro cot: "  + cotLine.getCodigoUnico());

        if (response.getComprobante() != null) {           
            log.info("response.getComprobante()");
            TBCOMPROBANTE.ValidacionesRemitos.Remito remito = response.getComprobante().getValidacionesRemitos().getRemito();
            System.out.println("Se pudo procesar.");

            if ("SI".equals(remito.getProcesado())) {
                System.out.println("Remito bien");
                log.info("Remito bien: " + remito.getNumeroUnico());
                cotLine.setStatus("1");
                cotLine.setMessage("Validado : " + remito.getNumeroUnico());
            } else {
                System.out.println("Remito con error");
                String errorMessage = remito.getErrores().getError().getDescripcion();
                log.info("Remito con error: " + errorMessage);
                cotLine.setStatus("2");
                cotLine.setMessage(errorMessage);
            }
        } else {    
            log.info("response.getComprobante() ES NULO");
            System.out.println("Error de sistema");
            String errorMessage = response.getError().getMensajeError();
            log.info("Error de sistema: " + errorMessage);
            cotLine.setStatus("2");
            cotLine.setMessage(errorMessage);
        }

        cotController.edit(cotLine);
        log.info("Respuesta guardada.");

    }

    public TextMessage getCurrentMessage() {
        return (this.currentMessage);
    }

    /**
     * Obtiene un nuevo mensaje de la cola de procesamiento.
     *
     */
    private boolean getMessage() {
        log.info("ServiceInstance - getMessage()");
        log.entering(this.getClass().getName(), "getMessage");
        boolean messageObtained = false;

        while (messageObtained != true) {
            try {
                this.currentMessage = (TextMessage) this.mc.receive(1000); //Timeout de un segundo.
                if (this.currentMessage != null) {
                    log.finest(" line_id =" + this.currentMessage.getText());
                }
                log.exiting(this.getClass().getName(), "getMessage", this.currentMessage);
                if (this.currentMessage != null) {
                    messageObtained = true;
                }
            } catch (JMSException e) {

                //Llamo al metodo para esperar y reiniciar la conexion
                log.info("Error JMSException, intentando reconectar");
                initializeReconnection();
                messageObtained = false;

            } catch (Exception ex) {

                //Error al reconectar, esperando
                log.info("Error general al desencolar, intentando reconectar");
                initializeReconnection();
                messageObtained = false;
            }

        }
        return (this.currentMessage != null);
    }

    /**
     * Obtiene el cuerpo del mensaje de la cola de procesamiento.
     *
     */
    private String getMessageBody() {
        log.entering(this.getClass().getName(), "getMessageBody");
        if (this.currentMessage != null) {
            try {
                String temp = this.currentMessage.getText();
                log.exiting(this.getClass().getName(), "getMessageBody", temp);
                return (temp);
            } catch (JMSException e) {
                log.severe("Error al leer cuerpo del mensaje de la cola. ");
                //log.throwing(this.getClass().getName(), "getMessageBody", e);
                log.info("getMessageBody: " + e.getMessage());
                log.log(Level.SEVERE, this.getClass().getName() + "getMessageBody()", e);
                return (null);
            }
        } else {
            log.exiting(this.getClass().getName(), "getMessageBody");
            return (null);
        }
    }

    /**
     * Da acknowledge al mensaje recibido.
     *
     */
    private boolean ackMessage() {
        log.entering(this.getClass().getName(), "ackMessage");
        if (this.currentMessage != null) {
            try {
                this.currentMessage.acknowledge();
                log.info("Desencolamos el mensaje");
                log.exiting(this.getClass().getName(), "ackMessage");
                return (true);

            } catch (JMSException e) {
                log.severe("Error al leer cuerpo del mensaje de la cola. ");
                //log.throwing(this.getClass().getName(), "ackMessage", e);
                log.info("ackMessage: " + e.getMessage());
                log.log(Level.SEVERE, this.getClass().getName() + "ackMessage()", e);
                return (false);
            }
        } else {
            log.exiting(this.getClass().getName(), "ackMessage");
            return (false);
        }
    }

    /**
     * Obtiene un line_id
     *
     */
    private BigDecimal getLineId() {
        log.entering(this.getClass().getName(), "getLineId");
        if (getMessage()) {
            try {
                BigDecimal temp = new BigDecimal(this.getMessageBody());
                log.exiting(this.getClass().getName(), "getLineId");
                return (temp);
            } catch (Exception e) {
                //log.throwing(this.getClass().getName(), "getLineId", e);
                log.info("getLineId: " + e.getMessage());
                log.severe("El mensaje encolado no es un line_id. Mensaje obtenido: " + this.getMessageBody());
                log.log(Level.SEVERE, this.getClass().getName() + "getLineId()", e);
                return (null);
            }
        } else {
            log.finest("El Queue Receiver salió por time out. Devolvemos null.");
        }
        log.exiting(this.getClass().getName(), "getLineId");
        return (null);
    }

    /**
     * Crea un nuevo queue consumer. Responsabilidades:
     * <ul>
     * <li>Crear un nuevo queue consumer.</li>
     * </ul>
     *
     * @since 1.0
     */
    private void createConsumer() throws Exception {

        try {

            try {
                if (DBConn != null) {
                    DBConn.close();
                }
                if (qc != null) {
                    qc.close();
                }
                if (qs != null) {
                    qs.close();
                }
                if (mc != null) {
                    mc.close();
                }
                DBConn = null;
                qc = null;
                qs = null;
                mc = null;

            } catch (Exception e) {

            }

            DBConn = this.parentService.getQueueConnection();

            this.qc = AQjmsQueueConnectionFactory.createQueueConnection(DBConn);
            log.info("Tenemos el Queue Connection.");
            this.qs = qc.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
            log.info("Tenemos la Queue Session.");
            this.qc.start();
            log.info("Dimos start en la QueueConnection..");
            this.q = ((AQjmsSession) qs).getQueue(parentService.getQueueOwner(), parentService.getQueueName());
            log.info("Tenemos la Queue.");
            this.mc = qs.createConsumer(q);
            log.info("Tenemos el MessageConsumer.");

        } catch (Exception ex) {
            //log.throwing(this.getClass().getName(), "createConsumer", ex);
            log.info("createConsumer: " + ex.getMessage());
            log.log(Level.SEVERE, this.getClass().getName() + "createConsumer", ex);
            throw ex;
        }
    }

    /**
     * Devuelve una conección a la base de datos con la OU Seteada.
     *
     * @return Connection Una conección a la base de datos.
     * @throws SQLException
     * @since 1.0
     */
    public Connection getConnection() throws SQLException {
        Connection temp = null;
        try {
            log.entering(this.getClass().getName(), "getConnection");
            log.finest("Obteniendo una nueva conección");
            temp = this.parentService.getConnection();
            log.exiting(this.getClass().getName(), "getConnection", temp);
            return (temp);
        } catch (SQLException e) {
            try {
                if (temp != null) {
                    temp.close();
                }
                temp = null;
                //log.throwing(this.getClass().getName(), "getConnection", e);
                log.info("getConnection: " + e.getMessage());
                log.exiting(this.getClass().getName(), "getConnection");
            } catch (Exception ex) {
                //log.throwing (this.getClass().getName(), "Error al cerrar la conexión con la base de datos", ex);
                log.info("Error al cerrar la conexión con la base de datos: " + ex.getMessage());
            }
            throw (e);
        }
    }

    /**
     * Genera la reconexion por alguna caida.
     *
     * @since 1.0
     */
    void initializeReconnection() {

        // Esperamos para probar de reconectar
        log.info("Error al leer un mensaje de la cola. Esperando 5 minutos...");
        try {
            //Espero 5 min -- 1000 = 1seg
            Thread.sleep(300000);
            this.parentService.restoreConnection();
            createConsumer();
        } catch (InterruptedException ei) {
            log.info("Error al realizar el sleep por perdida de conexion");
            Thread.currentThread().interrupt();
        } catch (Exception et) {
            log.info("Error al esperar y recrear la conexion.");
            //log.throwing(this.getClass().getName(), "getMessage", et);
            log.info("getMessage: " + et.getMessage());
        }

    }
}
