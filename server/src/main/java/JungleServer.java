import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JungleServer {

    private Server server;
    private Properties settings;
    private int serverListenPort;

    private String databaseURL;
    private Properties databaseProperties;

    public JungleServer() {
        settings = new Properties();
        server = new Server();
    }

    public JungleServer(String configPath) throws IOException {
        this();
        loadConfiguration(configPath);
    }

    public void start() throws IOException {
        server.start();
        server.bind(serverListenPort);
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

    public static int main(String[] args) {

        String configPath = args[0];
        JungleServer server;

        return 0;
    }
}
