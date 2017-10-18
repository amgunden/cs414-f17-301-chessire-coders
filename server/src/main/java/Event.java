public class Event {

    public static class InvitationEvent {
        public int invitationID;
    }

    public static class GameEndedEvent {
        public int gameID;
        public Types.GameOutcomeType outcomeType;
    }

    public static class ServerEvent {
        public ServerEventType eventType;
    }

    public enum ServerEventType {
        SERVER_SHUTDOWN
    }
}
