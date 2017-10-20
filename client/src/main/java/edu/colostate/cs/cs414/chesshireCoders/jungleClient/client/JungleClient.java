package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class JungleClient {

    Client client;

    public JungleClient() {
        client = new Client();

    }

    public void start() {
        client.start();
    }

    public void stop() {
        client.stop();
    }

    public void connect(int timeoutMillis, String hostname, int tcpPort) throws IOException {
        client.connect(timeoutMillis, hostname, tcpPort);
    }

    public void reconnect() throws IOException {
        client.reconnect();
    }

    public void sendMessageExpectsResponse() {

    }

    public void sendMessage() {

    }
}
