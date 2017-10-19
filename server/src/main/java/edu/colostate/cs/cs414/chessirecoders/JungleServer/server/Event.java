package edu.colostate.cs.cs414.chessirecoders.JungleServer.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Event {

    public static void registerEvents(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(InvitationEvent.class);
        kryo.register(GameEndedEvent.class);
        kryo.register(ServerEvent.class);
    }

    public static class InvitationEvent {
        int invitationID;

        public InvitationEvent(int invitationID) {
            this.invitationID = invitationID;
        }
    }

    public static class GameEndedEvent {
        int gameID;
        Types.GameOutcomeType outcomeType;

        public GameEndedEvent(int gameID, Types.GameOutcomeType gameOutcomeType) {
            this.gameID = gameID;
            this.outcomeType = gameOutcomeType;
        }
    }

    public static class ServerEvent {
        final String message;
        final ServerEventType eventType;

        public ServerEvent(ServerEventType eventType, String message) {
            this.eventType = eventType;
            this.message = message;
        }
    }

    public enum ServerEventType {
        SERVER_STOP
    }
}
