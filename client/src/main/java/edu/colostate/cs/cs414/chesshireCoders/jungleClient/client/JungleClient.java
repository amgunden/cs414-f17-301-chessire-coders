package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import com.esotericsoftware.kryonet.Client;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events.Events;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.OneTimeRunnableListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.Requests;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.Responses;

import java.io.IOException;

public class JungleClient {

    private Client client;

    /**
     * Constructs the client and registers all expected message types with the kryo serializer.
     */
    public JungleClient() {
        client = new Client();
        Events.kryoRegisterEvents(client);
        Requests.kryoRegisterRequests(client);
        Responses.kryoRegisterResponses(client);
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
     * Sends a message to the server and expects a response from the server. The OneTimeRunnableListener is added to the client
     * and when it recieves an object of the expected type, it will invoke the given Runnable object, and remove itself as
     * a listener from the client. (in theory)
     *
     * Example usage:
     *
     * sendMessageExpectsResponse("", new OneTimeRunnableListener<String>(String.class, client, () -> {
     *      doStuff();
     * }));
     *
     * @param o
     * @param listener
     */
    public void sendMessageExpectsResponse(Object o, OneTimeRunnableListener<?> listener) {
        client.addListener(listener);
        client.sendTCP(o);
    }

    /**
     * Sends a message to the server, response objects will not be handled.
     *
     * @param o
     */
    public void sendMessage(Object o) {
        client.sendTCP(o);
    }
}
