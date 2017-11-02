package edu.colostate.cs.cs414.chesshireCoders.jungleServer.server;

import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers.GameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers.RegistrationHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers.SessionHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers.UserHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.KryoRegistrar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class Main {

    private static Logger logger;

    public static void main(String[] args) {

        logger = Logger.getLogger(Main.class.getSimpleName());
        logger.log(Level.FINE, "Program arguments are '{0}'", args);

        String configPath = args[0];
        JungleServer server;
        Properties properties;

        int serverListenPort;
        int threadPoolSize;

        String databaseHostname;
        String databaseName;
        int databasePort;
        String databaseUser;
        String databasePassword;

        try {

            // load and parse properties
            properties = loadServerProperties(configPath);
            logger.log(Level.FINE, "Loaded server properties from {0}.", configPath);

            String logPath = getLogPath(properties);
            setLogFile(logPath);


            // Initialize the database datasource object
            databaseHostname = getDatabaseHostname(properties);
            databaseName = getDatabaseName(properties);
            databasePort = getDatabasePort(properties);
            databaseUser = getDatabaseUser(properties);
            databasePassword = getDatabasePassword(properties);
            JungleDB.initialize(databaseHostname, databaseName, databasePort, databaseUser, databasePassword);

            // Create and start the server.
            server = new JungleServer();

            // Add server listeners
            threadPoolSize = getListenerThreadNum(properties);
            KryoRegistrar.registerClasses(server);
            addListeners(server, threadPoolSize);

            // Bind and start the server
            serverListenPort = getServerListenPort(properties);
            server.bind(serverListenPort);
            server.start();

            // Add a shutdown hook to shutdown server gracefully on SIGTERM
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param configPath The path to the server configuration file
     * @throws IOException throws on failure to read configuration file.
     */
    private static Properties loadServerProperties(String configPath) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(configPath);
        properties.load(in);
        in.close();
        return properties;
    }

    private static int getServerListenPort(Properties properties) {
        return Integer.parseInt(properties.getProperty(
                "listen-port", "9898"
        ));
    }

    private static int getListenerThreadNum(Properties properties) {
        return Integer.parseInt(properties.getProperty(
                "listener-thread-num", "10"
        ));
    }

    private static String getDatabaseHostname(Properties properties) {
        return properties.getProperty("database-host", "localhost");
    }

    private static String getDatabaseName(Properties properties) {
        return properties.getProperty("database-name", "jungle");
    }

    private static int getDatabasePort(Properties properties) {
        return Integer.parseInt(properties.getProperty(
                "database-port", "5432")
        );
    }

    private static String getDatabaseUser(Properties properties) {
        return properties.getProperty("database-user", "jungle");
    }

    private static String getDatabasePassword(Properties properties) {
        return properties.getProperty("database-password", "jungle");
    }

    private static String getLogPath(Properties properties) {
        return properties.getProperty("log-path");
    }

    private static void addListeners(EndPoint endPoint, int poolSize) {

        logger.log(Level.INFO, "Setting up thread pool and adding listeners...");
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        endPoint.addListener(new Listener.ThreadedListener(
                new GameHandler(),
                executorService
        ));
        endPoint.addListener(new Listener.ThreadedListener(
                new RegistrationHandler(),
                executorService
        ));
        endPoint.addListener(new Listener.ThreadedListener(
                new SessionHandler(),
                executorService
        ));
        endPoint.addListener(new Listener.ThreadedListener(
                new UserHandler(),
                executorService
        ));

        // Add a shutdown hook to allow any running threads to end gracefully.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                executorService.shutdown();
                executorService.awaitTermination(10, TimeUnit.SECONDS);

                // force shutdown
                if (!executorService.isTerminated()) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }));
    }

    private static void setLogFile(String logPath) throws IOException {
        // if a log path was specified, add a log
        if (logPath != null) {
            logger.addHandler(new FileHandler(logPath));
            logger.log(Level.FINE, "log-path property set, logging to file '{0}'");
        }
    }
}
