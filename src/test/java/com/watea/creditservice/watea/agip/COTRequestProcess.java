package com.watea.creditservice.watea.agip;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.watea.creditservice.watea.agip.services.ServiceManager;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class COTRequestProcess {

    private static final String DAEMON_NAME = "COTRequestProcess";

    private Logger log = Logger.getLogger(this.getClass().getName());

    /**
     * El archivo de properties con la configuración del daemon
     */
    private String configfile;

    /**
     * La configuración del daemon, leida del archivo de properties
     */
    private static Properties props = new Properties();

    /**
     * El flag que indica si recibimos el signal de shutdown. El signal handler
     * debe poner este valor en false.
     */
    private boolean running = true;

    /**
     * Inicia el Daemon.
     *
     * @param args Opciones de línea de comando.
     * @since 1.0
     */
    public static void main(String args[]) {
        COTRequestProcess daemon = new COTRequestProcess();
        if (daemon.procesarCLI(args) && daemon.leerPropiedades()) {
            try {
                daemon.procesar();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("No se pudo iniciar el servicio. Debe proveer un archivo de configuración existente.");
        }
    }

    /**
     * Pide que se acabe el procesamiento en forma controlada.
     *
     * @since 1.0
     */
    public void shutdown() {
        log.entering(this.getClass().getName(), "shutdown");
        this.running = false;
        log.exiting(this.getClass().getName(), "shutdown");
        return;
    }

    /**
     * @return boolean true si el daemon esta corriendo.
     * @since 1.0
     */
    public boolean isRunning() {
        return (this.running);
    }

    /**
     * Procesa los parámetros de entrada. Utiliza la api de apache.commons.CLI
     * para procesar las opciones.
     *
     * @param args Opciones de línea de comando.
     * @return boolean true si se completaron las opciones obligatorias.
     * @since 1.0
     */
    private boolean procesarCLI(String args[]) {
        log.entering(this.getClass().getName(), "procesarCLI");

        boolean configOK = false;

        Options options = new Options();
        Option configfile = OptionBuilder.withArgName("file")
                .hasArg(true)
                .withDescription("El path al archivo de configuración.")
                .isRequired(true)
                .withLongOpt("configfile")
                .create("c");
        options.addOption(configfile);

        CommandLineParser parser = new GnuParser();
        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("configfile")) {
                this.configfile = line.getOptionValue("configfile");
                log.finest("ConfigFile: " + this.configfile);
                configOK = true;
            }
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            log.throwing(this.getClass().getName(), "procesarCLI", exp);
            configOK = false;
        }

        if (!configOK) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(DAEMON_NAME, options);
        }
        log.exiting(this.getClass().getName(), "procesarCLI", configOK);
        return (configOK);
    }

    /**
     * Leer las propiedades del archivo pasado como parámetro.
     *
     * @return boolean true si se pudo leer el archivo de propiedades.
     * @since 1.0
     */
    private boolean leerPropiedades() {
        log.entering(this.getClass().getName(), "leerPropiedades");
        boolean leidoOK = true;
        try {

            FileInputStream in = new FileInputStream(this.configfile);
            props.load(in);
            in.close();

        } catch (Exception e) {
            log.log(Level.SEVERE, this.getClass().getName() + "leerPropiedades()", e);
            leidoOK = false;
        }
        log.exiting(this.getClass().getName(), "leerPropiedades");
        return (leidoOK);
    }

    /**
     * Retorna el TRACE (o null si no fue informado)
     *
     * @since 1.0
     */
    public String getTraceMode() throws Exception {
        if (!this.props.containsKey("trace")) {
            return null;
        }
        return (this.props.getProperty("trace"));
    }

    /**
     * Retorna el JDBC_URL
     *
     * @since 1.0
     */
    public String getDataSourceJDBCUrl() throws Exception {
        if (!this.props.containsKey("jdbc_url")) {
            throw (new Exception("El archivo de properties debe definir la clave jdbc_url con la string de conección a la base de datos"));
        } else {
            return (this.props.getProperty("jdbc_url"));
        }
    }

    /**
     * Retorna el Password
     *
     * @since 1.0
     */
    public String getDataSourcePassword() throws Exception {
        if (!this.props.containsKey("password")) {
            throw (new Exception("El archivo de properties debe definir la clave password con la clave de acceso a la base de datos"));
        } else {
            return (this.props.getProperty("password"));
        }
    }

    /**
     * Retorna el Usuario
     *
     * @since 1.0
     */
    public String getDataSourceUserName() throws Exception {
        if (!this.props.containsKey("username")) {
            throw (new Exception("El archivo de properties debe definir el nombre de usuario"));
        }
        return (this.props.getProperty("username"));
    }

    /**
     * Devuelve el nombre del esquema de base de datos.
     *
     * @return El nombre de la cola.
     * @since 1.0
     */
    public String getUserName() throws Exception {
        log.entering(this.getClass().getName(), "getUserName");
        if (!this.props.containsKey("username")) {
            throw (new Exception("El archivo de properties debe definir la clave username con el usuario de la base de datos"));
        }
        String temp = this.props.getProperty("username");
        log.exiting(this.getClass().getName(), "getUserName", temp);
        return (temp);
    }

    /**
     * Devuelve el nombre del esquema de base de datos dueño de la cola de
     * Oracle Advanced Queueing con las facturas.
     *
     * @return El esquema de base de datos de la queue de Oracle Advanced
     * Queueing.
     * @since 1.0
     */
    public String getQueueOwner() throws Exception {
        log.entering(this.getClass().getName(), "getQueueOwner");
        if (!this.props.containsKey("queueowner")) {
            throw (new Exception("El archivo de properties debe definir la clave queueowner con el esquema dueño de la cola en la base de datos"));
        }
        String temp = this.props.getProperty("queueowner");
        log.exiting(this.getClass().getName(), "getQueueOwner", temp);
        return (temp);
    }

    /**
     * Devuelve el nombre de la cola de AQ con las facturas.
     *
     * @return El nombre de la cola.
     * @since 1.0
     */
    public String getQueueName() throws Exception {
        log.entering(this.getClass().getName(), "getQueueName");
        if (!this.props.containsKey("queuename")) {
            throw (new Exception("El archivo de properties debe definir la clave queuename con el nombre de la cola en la base de datos"));
        }
        String temp = this.props.getProperty("queuename");
        log.exiting(this.getClass().getName(), "getQueueName", temp);
        return (temp);
    }

    private void procesar() {
        log.entering(this.getClass().getName(), "procesar");
        ServiceManager s;
        try {
            log.info("Iniciando loop de Servicio.");
            s = new ServiceManager(this);
            s.procesar();
            log.info("Saliendo del loop de Servicio.");
        } catch (Exception e) {
            log.log(Level.SEVERE, this.getClass().getName() + "procesar()", e);
        }
        log.exiting(this.getClass().getName(), "procesar");
        return;
    }
    
    
    public static Properties getProperties(){
        return props;
    }
}
