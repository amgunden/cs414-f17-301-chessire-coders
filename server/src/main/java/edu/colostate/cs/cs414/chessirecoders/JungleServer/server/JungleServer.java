package edu.colostate.cs.cs414.chessirecoders.JungleServer.server;

import com.esotericsoftware.kryonet.Server;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JungleServer extends Server {

    private DataSource dataSource;

    /**
     *
     */
    public JungleServer() {
        ClientRequest.registerRequests(this);
        Event.registerEvents(this);
        ServerResponse.registerResponses(this);
        NetworkListener.addListeners(this);
    }

    /**
     * Gets a connection to the data source.
     *
     * @return Returns an SQL connection that can be used to connect to the Data Source
     * @throws SQLException Will throw if the data source has not been initialized, or if failed to get a connection.
     */
    public java.sql.Connection getDBConnection() throws SQLException {
        if (dataSource != null) {
            return this.dataSource.getConnection();
        } else {
            throw new SQLException("Data source is not initialized.");
        }
    }

    /**
     * Sets the data source to be used by this server.
     * @param dataSource The datasource to hand out connections from.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
