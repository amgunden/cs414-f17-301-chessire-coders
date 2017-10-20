package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chessirecoders.jungleNetwork.types.Types;

public class ServerEvent {
    String message;
    Types.ServerEventType eventType;

    public ServerEvent(Types.ServerEventType eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }
}
