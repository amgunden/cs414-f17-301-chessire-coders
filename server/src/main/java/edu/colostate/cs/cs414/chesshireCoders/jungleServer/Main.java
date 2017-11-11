package edu.colostate.cs.cs414.chesshireCoders.jungleServer;

import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.GameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.RegistrationHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.SessionHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.UserHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
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

        try {

            // load and parse properties
            properties = loadServerProperties(configPath);
            logger.log(Level.FINE, "Loaded server properties from {0}.", configPath);

            String logPath = getLogPath(properties);
            setLogFile(logPath);


            // Initialize the database datasource object
            HikariConnectionProvider.initialize(getDataSourceProperties(properties));

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

    private static Properties getDataSourceProperties(Properties properties) {
        Properties dataSourceProperties = new Properties();
        dataSourceProperties.setProperty(
                "dataSourceClassName",
                properties.getProperty("dataSourceClassName")
        );
        dataSourceProperties.setProperty(
                "dataSource.databaseName",
                properties.getProperty("dataSource.databaseName")
        );
        dataSourceProperties.setProperty(
                "dataSource.user",
                properties.getProperty("dataSource.user")
        );
        dataSourceProperties.setProperty(
                "dataSource.password",
                properties.getProperty("dataSource.password")
        );
        dataSourceProperties.setProperty(
                "dataSource.serverName",
                properties.getProperty("dataSource.serverName")
        );
        dataSourceProperties.setProperty(
                "dataSource.portNumber",
                properties.getProperty("dataSource.portNumber")
        );
        return dataSourceProperties;
    }

    private static int getServerListenPort(Properties properties) {
        return Integer.parseInt(properties.getProperty(
                "server.listenPort", "9898"
        ));
    }

    private static int getListenerThreadNum(Properties properties) {
        return Integer.parseInt(properties.getProperty(
                "server.listenerThreadNum", "10"
        ));
    }

    private static String getLogPath(Properties properties) {
        return properties.getProperty("log-path");
    }

    private static void addListeners(EndPoint endPoint, int poolSize) {

        logger.log(Level.INFO, "Setting up thread pool and adding listeners...");
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        endPoint.addListener(new Listener.ThreadedListener(new GameHandler(), executorService));
        endPoint.addListener(new Listener.ThreadedListener(new RegistrationHandler(), executorService));
        endPoint.addListener(new Listener.ThreadedListener(new SessionHandler(), executorService));
        endPoint.addListener(new Listener.ThreadedListener(new UserHandler(), executorService));

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
