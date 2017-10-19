package edu.colostate.cs.cs414.chessirecoders.JungleServer.server;

import com.esotericsoftware.kryonet.Server;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

public class JungleServer {

    private Server server;
    private int serverListenPort;
    private DataSource dataSource;

    /**
     *
     * @throws ClassNotFoundException
     */
    public JungleServer() throws ClassNotFoundException {
        server = new Server();

        ClientRequest.registerRequests(server);
        Event.registerEvents(server);
        ServerResponse.registerResponses(server);
    }

    /**
     *
     * @param serverListenPort
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws PropertyVetoException
     */
    public JungleServer(int serverListenPort) throws IOException, ClassNotFoundException, PropertyVetoException {
        this();
        this.serverListenPort = serverListenPort;
        NetworkListener.addListeners(server);
    }

    /**
     *
     * @throws IOException
     */
    public void start() throws IOException {
        server.start();
        server.bind(serverListenPort);
    }

    /**
     * This will stop the server from accepting new connections, while keeping old connections alive.
     */
    public void stop() {
        server.stop();
    }

    /**
     * This closes all connections and stops the server from listening.
     */
    public void close() {
        server.sendToAllTCP(new Event.ServerEvent(Event.ServerEventType.SERVER_STOP, "Server is shutting down!"));
        server.close();
    }


    public void close(String msg) {
        server.sendToAllTCP(new Event.ServerEvent(Event.ServerEventType.SERVER_STOP, msg));
        server.close();
    }

    java.sql.Connection getDBConnection() throws SQLException {
        return this.dataSource.getConnection();
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
