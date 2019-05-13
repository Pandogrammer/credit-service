package com.watea.creditservice.watea.agip.services;

import com.watea.creditservice.watea.agip.COTRequestProcess;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class ServiceManager {

    private static final String TRACE_TEXT = "ALTER SESSION SET EVENTS = '10046 trace name context forever, level 8'";

    private static final String LANGUAGE_TEXT = "ALTER SESSION SET NLS_LANGUAGE = 'American'";    //EDP 14/3/12

    private Logger log = Logger.getLogger(this.getClass().getName());

    private COTRequestProcess daemon;

    private ExecutorService pool;
    private DataSource dataSource;
    private DataSource queueDataSource;
    private EntityManagerFactory entityManagerFactory;

    private Boolean isTraceOn;

    private COTRequestProcess getDaemon() {
        log.entering(this.getClass().getName(), "getDaemon");
        log.exiting(this.getClass().getName(), "getDaemon", this.daemon);
        return (this.daemon);
    }

    /**
     * Devuelve el Queue Owner
     *
     * @since 1.0
     */
    public String getQueueOwner() throws Exception {
        try {
            log.entering(this.getClass().getName(), "getQueueOwner");
            log.exiting(this.getClass().getName(), "getQueueOwner", this.daemon.getQueueOwner());
            return (this.daemon.getQueueOwner());
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "getQueueOwner", e);
            throw (e);
        }
    }

    /**
     * Retorna si el daemon esta corriendo o no
     *
     * @since 1.0
     */
    public Boolean isRunning() {
        log.entering(this.getClass().getName(), "isRunning");
        log.exiting(this.getClass().getName(), "isRunning", this.daemon.isRunning());
        return (this.daemon.isRunning());
    }

    /**
     * Devuelve el Queue Owner
     *
     * @since 1.0
     */
    public String getQueueName() throws Exception {
        try {
            log.entering(this.getClass().getName(), "getQueueName");
            log.exiting(this.getClass().getName(), "getQueueName", this.daemon.getQueueName());
            return (this.daemon.getQueueName());
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "getQueueName", e);
            throw (e);
        }
    }

    public void setLanguage(Connection c) {			//EDP 14/3/12
        log.info("ServiceManager - setLanguage");
        Statement sql = null;

        try {

            // Ejecutamos el Statement
            sql = c.createStatement();
            sql.executeUpdate(this.LANGUAGE_TEXT);
            log.finest("Seteamos el lenguaje");
            sql.close();

        } catch (SQLException ex) {

            log.info("Imposible setear el lenguaje");
            log.throwing(this.getClass().getName(), "Error al setear el lenguaje", ex);

            try {
                if (sql != null) {
                    sql.close();
                }
                sql = null;
            } catch (Exception e) {
            }

        }
    }

    public void storeTrace(Connection c) {

        Statement sql = null;

        try {

            // Ejecutamos el Statement
            sql = c.createStatement();
            sql.executeUpdate(this.TRACE_TEXT);
            log.finest("Seteamos el trace");
            sql.close();

        } catch (SQLException ex) {

            log.info("Imposible realizar el trace");
            log.throwing(this.getClass().getName(), "Error al realizar el trace", ex);

            try {
                if (sql != null) {
                    sql.close();
                }
                sql = null;
            } catch (Exception e) {
            }

        }
    }

    /**
     * Devuelve una conección a la base de datos para desencolar.
     *
     * @return Connection Una conección a la base de datos.
     * @throws SQLException
     * @since 1.0
     */
    public Connection getQueueConnection() throws SQLException {
        Connection temp = null;
        try {
            log.entering(this.getClass().getName(), "getQueueConnection");
            log.finest("Obteniendo una nueva conección");
            temp = this.queueDataSource.getConnection();

            if (this.isTraceOn == true) {
                storeTrace(temp);
            }

            /*PreparedStatement sql = temp.prepareStatement ( "BEGIN hz_common_pub.disable_cont_source_security; END;");
	  sql.executeQuery();*/
            log.exiting(this.getClass().getName(), "getQueueConnection", temp);
            return (temp);
        } catch (SQLException e) {
            try {
                if (temp != null) {
                    temp.close();
                }
                temp = null;
                log.throwing(this.getClass().getName(), "getQueueConnection", e);
                log.log(Level.SEVERE, this.getClass().getName() + "getQueueConnection()", e);
                log.exiting(this.getClass().getName(), "getQueueConnection");
            } catch (Exception ex) {
                if (temp != null) {
                    temp.close();
                }
                temp = null;
                log.throwing(this.getClass().getName(), "Error al cerrar la conexión con la base de datos", ex);
            }
            throw (e);
        }
    }

    /**
     * Devuelve una conección a la base de datos.
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
            temp = this.dataSource.getConnection();
            setLanguage(temp);
            if (this.isTraceOn == true) {
                storeTrace(temp);
            }

            /*PreparedStatement sql = temp.prepareStatement ( "BEGIN hz_common_pub.disable_cont_source_security; END;");
	  sql.executeQuery();*/
            log.exiting(this.getClass().getName(), "getConnection", temp);
            return (temp);
        } catch (SQLException e) {
            try {
                if (temp != null) {
                    temp.close();
                }
                temp = null;
                log.throwing(this.getClass().getName(), "getConnection", e);
                log.log(Level.SEVERE, this.getClass().getName() + "getConnection()", e);
                log.exiting(this.getClass().getName(), "getConnection");
            } catch (Exception ex) {
                if (temp != null) {
                    temp.close();
                }
                temp = null;
                log.throwing(this.getClass().getName(), "Error al cerrar la conexión con la base de datos", ex);
            }
            throw (e);
        }
    }

    /**
     * Crea un Service. Responsabilidades:
     * <ul>
     * <li> Obtener las Unidades Operativas.</li>
     * <li> Generar un Service Instance por cada una de las que tengan
     * parametros de FE.</li>
     * </ul>
     *
     * @since 1.0
     */
    public ServiceManager(COTRequestProcess COTDaemon) throws Exception {
        log.entering(this.getClass().getName(), "Service Constructor");
        this.daemon = COTDaemon;
        this.dataSource = createDataSource();
        this.queueDataSource = createQueueDataSource();
        String traceMode = this.daemon.getTraceMode();
        if (traceMode == null) {
            this.isTraceOn = new Boolean(false);
        } else if (traceMode.equals("ON")) {
            this.isTraceOn = new Boolean(true);
        } else {
            this.isTraceOn = new Boolean(false);
        }
        this.pool = Executors.newCachedThreadPool();
        log.exiting(this.getClass().getName(), "Service Constructor");
    }

    /**
     * Inicializa el QueueDataSource con las opciones leídas de las properties
     *
     * @return DataSource si pudo inicializar un DataSource.
     * @since 2.0
     */
    public DataSource createQueueDataSource() throws Exception {
        log.entering(this.getClass().getName(), "createQueueDataSource");

        try {
            PoolDataSource temp = PoolDataSourceFactory.getPoolDataSource();
            log.finest("Pool Data Source: " + temp);

            temp.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            log.finest("Connection Factory Class Name: " + temp.getConnectionFactoryClassName());

            log.finest("JDBC URL: " + this.daemon.getDataSourceJDBCUrl());
            temp.setURL(this.daemon.getDataSourceJDBCUrl());

            log.finest("username: " + this.daemon.getDataSourceUserName());
            temp.setUser(this.daemon.getDataSourceUserName());

            temp.setPassword(this.daemon.getDataSourcePassword());

            log.finest("Tiempo en segundos antes de liberar una conección: 30 segundos");
            temp.setMaxIdleTime(30);

            log.exiting(this.getClass().getName(), "createQueueDataSource", temp);
            return (temp);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "createQueueDataSource", e);
            throw (e);
        }
    }

    /**
     * Inicializa el DataSource con las opciones leídas de las properties
     *
     * @return DataSource si pudo inicializar un DataSource.
     * @since 1.0
     */
    public DataSource createDataSource() throws Exception {
        log.entering(this.getClass().getName(), "createDataSource");

        try {
            PoolDataSource temp = PoolDataSourceFactory.getPoolDataSource();
            log.finest("Pool Data Source: " + temp);

            temp.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            log.finest("Connection Factory Class Name: " + temp.getConnectionFactoryClassName());

            log.finest("JDBC URL: " + this.daemon.getDataSourceJDBCUrl());
            temp.setURL(this.daemon.getDataSourceJDBCUrl());

            log.finest("username: " + this.daemon.getDataSourceUserName());
            temp.setUser(this.daemon.getDataSourceUserName());

            temp.setPassword(this.daemon.getDataSourcePassword());

            log.finest("Tiempo en segundos antes de liberar una conección: 30 segundos");
            temp.setMaxIdleTime(30);

            log.finest("Tiempo maximo de vida de una conexion: 1 hora");
            temp.setTimeToLiveConnectionTimeout(3600);

            log.finest("Cantidad de veces que se puede reusar una conexion: 5000");
            temp.setMaxConnectionReuseCount(5000);

            log.finest("Validamos las conexiones antes de entregarlas");
            temp.setValidateConnectionOnBorrow(true);
            temp.setSQLForValidateConnection("SELECT 1 FROM DUAL");

            log.finest("Establecemos un minimo de 3 conexiones preparadas");
            temp.setMinPoolSize(3);

            log.exiting(this.getClass().getName(), "createDataSource", temp);
            return (temp);
        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "createDataSource", e);
            throw (e);
        }
    }

    public void procesar() throws Exception {
        log.info("ServiceManager - procesar");
        Connection c = null;

        try {
            log.entering(this.getClass().getName(), "procesar()");
            c = this.getConnection();
            initializeConnection();
            ServiceInstance si = new ServiceInstance(this);
            this.pool.execute(si);

            log.finest("Cerrando la coneccion");
            c.close();
            c = null;
            log.exiting(this.getClass().getName(), "procesar()");
        } catch (Exception e) {
            try {
                if (c != null) {
                    c.close();
                }
                log.throwing(this.getClass().getName(), "procesar", e);
                log.log(Level.SEVERE, this.getClass().getName() + "procesar()", e);
                log.exiting(this.getClass().getName(), "procesar");
            } catch (Exception ex) {
                log.throwing(this.getClass().getName(), "Error al cerrar la conexión con la base de datos", ex);
            }
            throw (e);
        }
    }

    private void initializeConnection() throws Exception {
        Map<String, Object> properties = new HashMap<>();

        properties.put("javax.persistence.jdbc.driver", "oracle.jdbc.OracleDriver");
        properties.put("javax.persistence.jdbc.url", this.daemon.getDataSourceJDBCUrl());
        properties.put("javax.persistence.jdbc.user", this.daemon.getDataSourceUserName());
        properties.put("javax.persistence.jdbc.password", this.daemon.getDataSourcePassword());

        entityManagerFactory = Persistence.createEntityManagerFactory("COTRequestProcessPU", properties);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }


    /*
   *  Metodo que genera la conexion tras una caida
   * @since 1.0
     */
    public void restoreConnection() {
        log.entering(this.getClass().getName(), "restoreConnection");
        try {

            this.dataSource = createDataSource();
            this.queueDataSource = createQueueDataSource();

            log.exiting(this.getClass().getName(), "restoreConnection");

        } catch (Exception e) {
            log.throwing(this.getClass().getName(), "Error al regenerar la conexion", e);
        }
    }

}
