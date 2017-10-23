package edu.colostate.cs.cs414.chesshireCoders.jungleServer.server;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JungleDB {

    private PGSimpleDataSource dataSource;

    private static JungleDB db = null;
    private static String hostname;
    private static String databaseName;
    private static int port;
    private static String username;
    private static String password;


    private JungleDB(String hostname, String dbName, int port, String username, String password) {
        dataSource = new PGSimpleDataSource();

        dataSource.setServerName(hostname);
        dataSource.setDatabaseName(dbName);
        dataSource.setPortNumber(port);
        dataSource.setUser(username);
        dataSource.setPassword(password);
    }

    public static JungleDB getInstance() {
        if (db == null) {
            db = new JungleDB(hostname, databaseName, port, username, password);
        }
        return db;
    }

    public static void setConnectionDetails(Properties properties) {
        hostname = properties.getProperty("hostname");
        databaseName = properties.getProperty("dbName");
        port = Integer.parseInt(properties.getProperty("port"));
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public static void setConnectionDetails(String hostname, String dbName, int port, String username, String password) {
        JungleDB.hostname = hostname;
        JungleDB.databaseName = dbName;
        JungleDB.port = port;
        JungleDB.username = username;
        JungleDB.password = password;
    }

    /**
     * Gets a connection to the dataObjects source.
     *
     * @return Returns an SQL connection that can be used to connect to the Data Source
     * @throws SQLException Will throw if the dataObjects source has not been initialized, or if failed to get a connection.
     */
    public Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return this.dataSource.getConnection();
        } else {
            throw new SQLException("Data source is not initialized.");
        }
    }
}
