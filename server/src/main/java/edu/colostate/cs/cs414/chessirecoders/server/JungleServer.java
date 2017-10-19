package edu.colostate.cs.cs414.chessirecoders.server;

import com.esotericsoftware.kryonet.Server;
import edu.colostate.cs.cs414.chessirecoders.datasource.DataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class JungleServer {

    private Server server;
    private Properties settings;
    private int serverListenPort;

    private DataSource dataSource;
    private String databaseURL;
    private Properties databaseProperties;

    /**
     *
     * @throws ClassNotFoundException
     */
    public JungleServer() throws ClassNotFoundException {
        settings = new Properties();
        server = new Server();

        ClientRequest.registerRequests(server);
        Event.registerEvents(server);
        ServerResponse.registerResponses(server);

    }

    /**
     *
     * @param configPath
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws PropertyVetoException
     */
    public JungleServer(String configPath) throws IOException, ClassNotFoundException, PropertyVetoException {
        this();
        loadConfiguration(configPath);
        initializeDataSource();
    }

    /**
     *
     * @throws PropertyVetoException
     */
    private void initializeDataSource() throws PropertyVetoException {
        dataSource = DataSource.getInstance();
        dataSource.initialize(1, 5, 1, databaseURL, databaseProperties);
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

    /**
     *
     * @param configPath
     * @throws IOException
     */
    private void loadConfiguration(String configPath) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(configPath);
            settings.load(in);

            serverListenPort = Integer.parseInt(
                    settings.getProperty("listen-port", "9898")
            );

            databaseURL = String.format(
                    "jdbc:postgres://%s:%s/%s",
                    settings.getProperty("database-host", "localhost"),
                    settings.getProperty("database-port", "5432"),
                    settings.getProperty("database-name", "jungle")
            );
            databaseProperties.setProperty(
                    "user",
                    settings.getProperty("database-user")
            );
            databaseProperties.setProperty(
                    "password",
                    settings.getProperty("database-password")
            );
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not load configuration file '" + configPath + "'.");
        } catch (IOException e1) {
            throw new IOException("Invalid port number specified: " + settings.getProperty("listen-port"));
        }
    }



    java.sql.Connection getDatabaseConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    public static void main(String[] args) {

        String configPath = args[0];
        JungleServer server;
        try {
            server = new JungleServer(configPath);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
