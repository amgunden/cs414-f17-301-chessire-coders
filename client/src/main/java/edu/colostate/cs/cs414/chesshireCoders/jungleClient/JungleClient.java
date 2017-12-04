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

    private String hostName = "localhost";
    private int listenPort = 9898;
    private int timeout = 5000;

    /**
     * Constructs the client and registers all expected message types with the kryo serializer.
     */
    private JungleClient() {
        client = new Client();
        KryoRegistrar.registerClasses(client);
        client.addListener(new Listener.ThreadedListener(new LogListener()));

        Runtime.getRuntime().addShutdownHook(new Thread(this::disconnect));
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
     */
    public void connect() throws IOException {
        client.start();
        client.connect(timeout, hostName, listenPort);
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    /**
     * Stops the client send/receive thread
     */
    public void disconnect() {
        client.close();
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
    public void sendMessage(Object o) throws IOException {
        if (!isConnected()) connect();
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
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
