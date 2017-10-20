package edu.colostate.cs.cs414.chessireCoders.jungleServer.server;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {

    public static void main(String[] args) {

        String configPath = args[0];
        JungleServer server;
        DataSource dataSource;
        Properties properties;
        int serverListenPort;

        // load and parse properties
        try {
            properties = loadServerProperties(configPath);
            serverListenPort = Integer.parseInt(properties.getProperty("listen-port", "9898"));
            dataSource = initializeDataSource(properties);

            // Create and start the server.
            server = new JungleServer();
            server.setDataSource(dataSource);
            server.bind(serverListenPort);
            server.start();

            // Add a shutdown hook to shutdown server gracefully on SIGTERM
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
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

    private static DataSource initializeDataSource(Properties properties) throws PropertyVetoException {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        // server host name/ip address
        dataSource.setServerName(
                properties.getProperty("database-host", "localhost")
        );

        // database name
        dataSource.setDatabaseName(
                properties.getProperty("database-name", "jungle")
        );

        // port number
        dataSource.setPortNumber(
                Integer.parseInt(properties.getProperty("database-port", "5432"))
        );

        // default username
        dataSource.setUser(
                properties.getProperty("database-user", "jungle")
        );

        // default password
        dataSource.setPassword(
                properties.getProperty("database-password", "jungle")
        );

        return dataSource;
    }
}
