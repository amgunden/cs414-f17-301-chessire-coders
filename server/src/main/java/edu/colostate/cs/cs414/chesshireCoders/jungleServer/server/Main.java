package edu.colostate.cs.cs414.chesshireCoders.jungleServer.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    private static Logger logger;

    public static void main(String[] args) {

        logger = Logger.getLogger(Main.class.getSimpleName());
        logger.log(Level.FINE, "Program arguments are '{0}'", args);

        String configPath = args[0];
        JungleServer server;
        Properties properties;
        int serverListenPort;

        try {

            // load and parse properties
            properties = loadServerProperties(configPath);
            logger.log(Level.FINE, "Loaded server properties from {0}.", configPath);

            // if a log path was specified, add a log
            String logPath = properties.getProperty("log-path");
            if (logPath != null) {
                logger.addHandler(new FileHandler(logPath));
                logger.log(Level.FINE, "log-path property set, logging to file '{0}'");
            }

            serverListenPort = Integer.parseInt(properties.getProperty("listen-port", "9898"));

            JungleDB.setConnectionDetails(getJungleDBProperties(properties));

            // Create and start the server.
            server = new JungleServer();
            server.bind(serverListenPort);
            addRequestHandlers(server);
            server.start();

            // Add a shutdown hook to shutdown server gracefully on SIGTERM
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param configPath
     * @throws IOException
     */
    private static Properties loadServerProperties(String configPath) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(configPath);
        properties.load(in);
        in.close();
        return properties;
    }

    private static Properties getJungleDBProperties(Properties properties) {

        Properties dbProperties = new Properties();
        dbProperties.setProperty(
                "hostname",
                properties.getProperty("database-host", "localhost")
        );

        dbProperties.setProperty(
                "dbName",
                properties.getProperty("database-name", "jungle")
        );

        dbProperties.setProperty(
                "port",
                properties.getProperty("database-port", "5432")
        );

        dbProperties.setProperty(
                "username",
                properties.getProperty("database-user", "jungle")
        );

        dbProperties.setProperty(
                "password",
                properties.getProperty("database-password", "jungle")
        );

        return dbProperties;
    }

    private static void addRequestHandlers(JungleServer server) {

    }
}
