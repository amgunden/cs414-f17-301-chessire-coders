package helpers;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;

import java.util.HashSet;
import java.util.Set;

public class ConnectionHelper {

    private Set<JungleConnection> connections;

    public ConnectionHelper() {
        this.connections = new HashSet<>();
    }

    public JungleConnection newConnection() {
        JungleConnection connection = new JungleConnection();
        connections.add(connection);
        return connection;
    }

    public Set<JungleConnection> getConnections() {
        return connections;
    }

    public void clearConnections() {
        this.connections.clear();
    }
}
