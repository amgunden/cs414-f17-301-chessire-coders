package edu.colostate.cs.cs414.chesshireCoders.jungleServer.server;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JungleDB {

    private PGSimpleDataSource dataSource;

    private static JungleDB db = null;
    private static Properties connectionDetails = null;

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
            db = new JungleDB(
                    connectionDetails.getProperty("hostname"),
                    connectionDetails.getProperty("dbName"),
                    Integer.parseInt(connectionDetails.getProperty("port")),
                    connectionDetails.getProperty("username"),
                    connectionDetails.getProperty("password")
            );
        }
        return db;
    }

    public static void setConnectionDetails(Properties properties) {
        connectionDetails = properties;
    }

    /**
     * Gets a connection to the data source.
     *
     * @return Returns an SQL connection that can be used to connect to the Data Source
     * @throws SQLException Will throw if the data source has not been initialized, or if failed to get a connection.
     */
    public Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return this.dataSource.getConnection();
        } else {
            throw new SQLException("Data source is not initialized.");
        }
    }
}
