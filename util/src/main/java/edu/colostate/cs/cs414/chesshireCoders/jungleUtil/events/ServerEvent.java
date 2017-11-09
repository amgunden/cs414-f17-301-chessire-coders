package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.ServerEventType;

public class ServerEvent {
    private String message;
    private ServerEventType eventType;

    public ServerEvent() {
    }

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
