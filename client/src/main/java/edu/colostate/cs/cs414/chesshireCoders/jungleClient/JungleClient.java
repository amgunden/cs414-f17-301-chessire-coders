package edu.colostate.cs.cs414.chesshireCoders.jungleClient;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.KryoRegistrar;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a thin wrapper for KryoNet.Client to allow TCP communication with a server.
 */
public class JungleClient {

    private static JungleClient ourInstance = new JungleClient();


    private Client client;

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    /**
     * Constructs the client and registers all expected message types with the kryo serializer.
     */
    private JungleClient() {
        client = new Client();
        KryoRegistrar.registerClasses(client);
        client.addListener(new Listener.ThreadedListener(new LogListener()));
    }

    public static JungleClient getInstance() {
        return ourInstance;
    }

    /**
     * Starts the client send/receive thread
     */
    public void start() {
        client.start();
    }

    /**
     * Connects the client to a server.
     *
     * @param timeoutMillis How long to wait for initial response before dropping connection.
     * @param hostname      Server hostname/IP address
     * @param tcpPort       TCP port that server is listening on.
     * @throws IOException Thrown if connection cannot be established.
     */
    public void connect(int timeoutMillis, String hostname, int tcpPort) throws IOException {
        client.connect(timeoutMillis, hostname, tcpPort);
    }

    /**
     * Stops the client send/receive thread
     */
    public void stop() {
        client.stop();
    }

    public void reconnect() throws IOException {
        client.reconnect();

    }

    /**
     * Sends a message to the server, response objects will not be handled.
     *
     * @param o
     */
    public void sendMessage(Object o) {
        client.sendTCP(o);
        logger.log(Level.INFO, "Sending {0} to server at {1}",
                new Object[]{o.getClass().getSimpleName(), client.getRemoteAddressTCP()}
        );
    }

    public void addListener(Listener listener) {
        client.addListener(listener);
    }

    public void removeListener(Listener listener) {
        client.removeListener(listener);
    }

    private class LogListener extends Listener {
        @Override
        public void connected(Connection c) {
            logger.log(
                    Level.INFO,
                    "New connection to {0}.",
                    new Object[]{c.getRemoteAddressTCP()}
            );
        }

        @Override
        public void received(Connection c, Object object) {
            if (!(object instanceof FrameworkMessage.KeepAlive)) {
                logger.log(
                        Level.INFO,
                        "Received {0} from server at {1}.",
                        new Object[]{object.getClass().getSimpleName(), c.getRemoteAddressTCP()}
                );
            }
        }

        @Override
        public void disconnected(Connection c) {
            logger.log(
                    Level.INFO,
                    "Disconnected from server.",
                    new Object[]{c.getRemoteAddressTCP()}
            );
        }
    }
}
