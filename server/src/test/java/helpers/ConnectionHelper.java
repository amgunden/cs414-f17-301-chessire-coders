package helpers;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;

import java.net.InetSocketAddress;

public class ConnectionHelper {

    public JungleConnection createConnection() {
        return new FakeJungleConnection();
    }

    private static class FakeJungleConnection extends JungleConnection {
        /**
         * Returns the IP address and port of the remote end of the TCP connection, or null if this connection is not connected.
         */
        @Override
        public InetSocketAddress getRemoteAddressTCP() {
            return new InetSocketAddress("127.0.0.1", 1337);
        }
    }
}
