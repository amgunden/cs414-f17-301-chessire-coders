import java.util.Hashtable;

public class Messaging {

    /**
     * Synchronous message from client
     */
    public static class PostMessage {
        public RequestType requestType;
        public Hashtable<String, String> body;
    }

    public enum RequestType {
        GET,
        POST
    }

    /**
     * Synchronous response to client request
     */
    public static class ResponseMessage {
        public ResponseCode responseCode;
    }

    public enum ResponseCode {
        SUCCESS,
        CLIENT_ERROR,
        SERVER_ERROR
    }

    /**
     * Asynchronous message to client.
     * i.e. server generated message, client is not expecting.
     */
    public static class EventMessage {
        public EventType eventType;
    }

    public enum EventType {
        INVITE,
        SERVER_EVENT, // i.e. server shutdown
        GAME_ENDED
    }
}
