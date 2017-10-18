public class Response {

    public static class GameData {
        int gameID;
        int playerOneID;
        int playerTwoID;
        int playerTwoStatus;
        GameStatus status;
    }

    public enum GameStatus {
        WAITING,
        IN_PROGRESS,
        DRAW,
        P1_WINNER,
        P2_WINNER
    }

    public static class GamePieceLocation {
        int x;
        int y;
    }

    public static class PlayerData {
        int playerID;
        String playerName;
        PlayerColor playerColor;
    }

    public enum PlayerColor {
        RED,
        BLACK
    }

    public static class PlayerGameHistoryData {
        int playerID;
        GameData[] history;
    }

    public static class InvitationData {
        int invitationID;
        int playerFromID;
        int playerToID;
        int gameID;
    }

    public static class SessionInfo {
        byte[] sessionToken;
        long expiresOn;         // Expressed as milliseconds since Epoch
    }

    public static class Error {
        public ErrorType errorType;
        public String errorMsg;
    }

    public enum ErrorType {
        SERVER_ERROR,
        CLIENT_ERROR,
        UNKNOWN
    }
}
