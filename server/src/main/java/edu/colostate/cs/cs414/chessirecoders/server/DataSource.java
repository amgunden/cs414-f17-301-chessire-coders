package edu.colostate.cs.cs414.chessirecoders.server;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private static DataSource dataSource;

    private ComboPooledDataSource cpds;

    private DataSource() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();
    }

    public void initialize(int minPoolSize, int maxPoolSize, int incrementNum, String url, Properties properties) throws PropertyVetoException {
        cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl(url);
        cpds.setProperties(properties);

        cpds.setMinPoolSize(minPoolSize);
        cpds.setAcquireIncrement(incrementNum);
        cpds.setMaxPoolSize(maxPoolSize);
        cpds.setMaxStatements(180);
    }

    public static DataSource getInstance() throws PropertyVetoException {
        if (dataSource == null) {
            dataSource = new DataSource();
        }
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }
}
