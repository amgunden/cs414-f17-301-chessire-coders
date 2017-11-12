package main;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import helpers.ConnectionHelper;
import helpers.ExceptionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class World {

    private ConnectionHelper connectionHelper = new ConnectionHelper();

    private ExceptionHelper exceptionHelper = new ExceptionHelper();
    private List<Map<String, String>> credentials;

    public List<Map<String, String>> getCredentials() {
        return credentials;
    }

    public World setCredentials(List<Map<String, String>> credentials) {
        this.credentials = credentials;
        return this;
    }

    public void helloWorld() {
        System.out.println("Hello world!");
    }

    public JungleConnection createConnection() {
        return connectionHelper.createConnection();
    }

    public List<Object> getSentMessages() {
        return connectionHelper.getSentMessages();
    }

    public List<Object> getSentMessages(Class tClass) {
        List<Object> objects = new ArrayList<>();
        for (Object message : getSentMessages()) {
            if (message.getClass().equals(tClass))
                objects.add(message);
        }
        return objects;
    }

    public boolean messageSent() {
        return connectionHelper.messageSent();
    }

    public void expectsAnyException() {
        exceptionHelper.expectsAnyException();
    }

    public void expectsException(Class<? extends Exception> type) {
        exceptionHelper.expectsException(type);
    }

    public void handleException(Exception e) {
        exceptionHelper.handle(e);
    }

    public List<Exception> getUnexpectedExceptions() {
        return exceptionHelper.getUnexpectedExceptions();
    }

    public boolean failed() {
        return exceptionHelper.failed();
    }

    public List<JungleConnection> getConnections() {
        return connectionHelper.getConnections();
    }
}
