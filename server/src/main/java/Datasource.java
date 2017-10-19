import java.sql.Connection;
import java.util.Properties;
import java.util.Queue;

public class Datasource {

    private static Datasource datasource;

    private final int maxConnections;
    private final String url;
    private final Properties properties;
    private Queue<Connection> connections = new ConcurrentLinkedQueue<>();

    private Datasource(int maxConnections, String url, Properties properties) {
        this.maxConnections = maxConnections;
        this.url = url;
        this.properties = properties;
    }

    public Datasource getInstance() {

    }

    public Connection getConnection() {

    }

    public void returnConnection(Connection connection) {

    }
}
