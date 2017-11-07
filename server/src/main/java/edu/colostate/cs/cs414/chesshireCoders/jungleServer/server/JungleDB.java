package edu.colostate.cs.cs414.chesshireCoders.jungleServer.server;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JungleDB {

    private static JungleDB db = null;

    private HikariDataSource dataSource;

    private JungleDB(Properties properties) {
        initDataSource(properties);
    }

    public static JungleDB getInstance() {
        if (db == null) {
            throw new NullPointerException("JungleDB has not been initialized");
        }
        return db;
    }

    public static synchronized void initialize(Properties properties) {
        db = new JungleDB(properties);
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
