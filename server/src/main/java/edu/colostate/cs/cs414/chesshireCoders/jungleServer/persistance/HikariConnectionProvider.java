package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariConnectionProvider implements ConnectionProvider {

    private static HikariConnectionProvider db = null;

    private HikariDataSource dataSource;

    private HikariConnectionProvider(Properties properties) {
        initDataSource(properties);
    }

    public static HikariConnectionProvider getInstance() {
        if (db == null) {
            throw new NullPointerException("JungleDB has not been initialized");
        }
        return db;
    }

    public static synchronized void initialize(Properties properties) {
        db = new HikariConnectionProvider(properties);
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

    private void initDataSource(Properties properties) {
        HikariConfig config = new HikariConfig(properties);
        dataSource = new HikariDataSource(config);
    }
}
