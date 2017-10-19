package edu.colostate.cs.cs414.chessirecoders.JungleServer.server;

import com.esotericsoftware.kryonet.Server;

public class Controller {

    private Server server;

    public Controller(Server server) {
        this.server = server;
    }

    public void getGameController() {
        new Thread(() -> {

        }).start();
    }
}
