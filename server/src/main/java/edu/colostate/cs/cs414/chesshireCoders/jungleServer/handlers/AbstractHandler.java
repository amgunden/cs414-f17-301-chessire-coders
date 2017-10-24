package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Collection;
import java.util.HashSet;

public abstract class AbstractHandler {

    protected Server server;
    protected HashSet<Listener> listeners = new HashSet<>();

    public AbstractHandler(Server server) {
        this.server = server;
        initializeListeners();
    }

    public void registerHandler() {
        for (Listener listener : listeners) {
            server.addListener(listener);
        }
    }

    public void unregisterHandler() {
        for (Listener listener : listeners) {
            server.removeListener(listener);
        }
    }

    abstract void initializeListeners();
}
