import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JungleServer {

    private Server server;
    private Properties settings;
    private int serverListenPort;

    private String databaseURL;
    private Properties databaseProperties;

    public JungleServer() throws ClassNotFoundException {
        settings = new Properties();
        server = new Server();

        ClientRequest.registerRequests(server);
        Event.registerEvents(server);
        ServerResponse.registerResponses(server);

        Class.forName();
    }

    public JungleServer(String configPath) throws IOException, ClassNotFoundException {
        this();
        loadConfiguration(configPath);
    }

    public void start() throws IOException {
        server.start();
        server.bind(serverListenPort);
        server.addListener(new DynamicRunListener(this));
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
        server.sendToAllTCP(new Event.ServerEvent());
        server.close();
    }

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

    private void testDatabaseConnection() throws SQLException {
        java.sql.Connection connection = DriverManager.getConnection(databaseURL, databaseProperties);
        connection.close();
    }

    public static abstract class DynamicRecievedListener<T> extends Listener {

        private final Class<T> c;

        public DynamicRecievedListener(Class<T> c) {
            this.c = c;
        }

        @Override
        public void received(Connection connection, Object object) {
            if(c.isInstance(object)) {
                run(connection, c.cast(object));
            }
        }

        public abstract void run(Connection connection, T recieved);
    }

    public java.sql.Connection getDatabaseConnection() throws SQLException {

        java.sql.Connection connection = DriverManager.getConnection(databaseURL, databaseProperties);
        connection.
    }

    public static int main(String[] args) {

        String configPath = args[0];
        JungleServer server;

        return 0;
    }
}
