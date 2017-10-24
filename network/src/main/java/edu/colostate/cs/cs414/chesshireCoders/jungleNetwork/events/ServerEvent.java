package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.ServerEventType;

public class ServerEvent {
    private String message;
    private ServerEventType eventType;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServerEventType getEventType() {
        return eventType;
    }

    public void setEventType(ServerEventType eventType) {
        this.eventType = eventType;
    }

    public ServerEvent(ServerEventType eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }
}
