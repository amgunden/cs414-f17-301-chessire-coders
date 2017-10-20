package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chessirecoders.jungleNetwork.types.ServerEventType;

public class ServerEvent {
    String message;
    ServerEventType eventType;

    public ServerEvent(ServerEventType eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }
}
