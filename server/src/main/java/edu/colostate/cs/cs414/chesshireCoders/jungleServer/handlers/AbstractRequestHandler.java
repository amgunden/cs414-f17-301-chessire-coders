package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;

public abstract class AbstractRequestHandler {

    protected JungleServer server;

    public AbstractRequestHandler(JungleServer server) {
        this.server = server;
    }

    public abstract void addListeners();
}
