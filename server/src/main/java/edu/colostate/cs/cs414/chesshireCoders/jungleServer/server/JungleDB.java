package edu.colostate.cs.cs414.chesshireCoders.jungleServer.server;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JungleDB {

    private static JungleDB db = null;
    private PGSimpleDataSource dataSource;


    private JungleDB(String hostname, String databaseName, int port, String username, String password) {
        dataSource = new PGSimpleDataSource();

        dataSource.setServerName(hostname);
        dataSource.setDatabaseName(databaseName);
        dataSource.setPortNumber(port);
        dataSource.setUser(username);
        dataSource.setPassword(password);
    }

    public static JungleDB getInstance() {
        if (db == null) {
            throw new NullPointerException("JungleDB has not been initialized");
        }
        return db;
    }

    public static synchronized void initialize(String hostname, String dbName, int port, String username, String password) {
        db = new JungleDB(hostname, dbName, port, username, password);
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
