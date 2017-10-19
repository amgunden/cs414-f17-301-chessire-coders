package edu.colostate.cs.cs414.chessirecoders.JungleServer.messages;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import edu.colostate.cs.cs414.chessirecoders.JungleServer.data.GameHistory;
import edu.colostate.cs.cs414.chessirecoders.JungleServer.data.Types;

public class ServerResponse {

    public static void registerResponses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(GetGameResponse.class);
        kryo.register(GetPieceLocationResponse.class);
        kryo.register(GetPlayerResponse.class);
        kryo.register(GetUserGameHistoryResponse.class);
        kryo.register(GetInvitationResponse.class);
        kryo.register(LoginSuccessResponse.class);
        kryo.register(ErrorResponse.class);
    }

    public static class GetGameResponse {
        int gameID;
        int playerOneID;
        int playerTwoID;
        Types.PlayerStatus playerTwoStatus;
        Types.GameStatus status;

        public GetGameResponse(int gameID, int playerOneID, int playerTwoID, Types.PlayerStatus playerTwoStatus, Types.GameStatus status) {
            this.gameID = gameID;
            this.playerOneID = playerOneID;
            this.playerTwoID = playerTwoID;
            this.playerTwoStatus = playerTwoStatus;
            this.status = status;
        }
    }


    public static class GetPieceLocationResponse {
        int pieceID;
        int x;
        int y;

        public GetPieceLocationResponse(int pieceID, int x, int y) {
            this.pieceID = pieceID;
            this.x = x;
            this.y = y;
        }
    }

    public static class GetPlayerResponse {
        int playerID;
        String playerName;
        Types.PlayerColor playerColor;

        public GetPlayerResponse(int playerID, String playerName, Types.PlayerColor playerColor) {
            this.playerID = playerID;
            this.playerName = playerName;
            this.playerColor = playerColor;
        }
    }

    public static class GetUserGameHistoryResponse {
        int playerID;
        GameHistory history;

        public GetUserGameHistoryResponse(int playerID, GameHistory history) {
            this.playerID = playerID;
            this.history = history;
        }
    }

    public static class GetInvitationResponse {
        int invitationID;
        int playerFromID;
        int playerToID;
        int gameID;

        public GetInvitationResponse(int invitationID, int playerFromID, int playerToID, int gameID) {
            this.invitationID = invitationID;
            this.playerToID = playerToID;
            this.playerFromID = playerFromID;
            this.gameID = gameID;
        }
    }

    public static class LoginSuccessResponse {
        String sessionToken;
        long expiresOn;         // Expressed as milliseconds since Epoch

        public LoginSuccessResponse(String sessionToken, long expiresOn) {
            this.sessionToken = sessionToken;
            this.expiresOn = expiresOn;
        }
    }

    public static class ErrorResponse {
        ErrorType errorType;
        String errorMsg;

        public ErrorResponse(ErrorType errorType, String errorMsg) {
            this.errorType = errorType;
            this.errorMsg = errorMsg;
        }
    }

    public enum ErrorType {
        SERVER_ERROR,
        CLIENT_ERROR,
        UNAUTHORIZED,
        BAD_LOGIN,
        BAD_REGISTRATION,
        UNKNOWN
    }
}
