package helpers;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHelper {

    private List<Object> sentMessages = new ArrayList<>();
    private List<JungleConnection> connections = new ArrayList<>();

    public JungleConnection createConnection() {
        FakeJungleConnection fakeJungleConnection = new FakeJungleConnection();
        connections.add(fakeJungleConnection);
        return fakeJungleConnection;
    }

    public List<JungleConnection> getConnections() {
        return connections;
    }

    public List<Object> getSentMessages() {
        return sentMessages;
    }

    public boolean messageSent() {
        return !sentMessages.isEmpty();
    }

    private class FakeJungleConnection extends JungleConnection {
        /**
         * Returns the IP address and port of the remote end of the TCP connection, or null if this connection is not connected.
         */
        @Override
        public InetSocketAddress getRemoteAddressTCP() {
            return new InetSocketAddress("127.0.0.1", 1337);
        }

        /**
         * fake the sending of messages
         */
        @Override
        public int sendTCP(Object object) {
            sentMessages.add(object);
            return 100;
        }
    }
}
