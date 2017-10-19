package edu.colostate.cs.cs414.chessirecoders.JungleServer.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import edu.colostate.cs.cs414.chessirecoders.JungleServer.data.Types;

public class ClientRequest {

    /**
     *
     * @param endPoint
     */
    public static void registerRequests(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(LoginRequest.class);
        kryo.register(LogoutRequest.class);
        kryo.register(RegisterRequest.class);
        kryo.register(GetGameRequest.class);
        kryo.register(GetPieceLocationRequest.class);
        kryo.register(UpdatePieceLocationRequest.class);
        kryo.register(GetPlayerRequest.class);
        kryo.register(GetUserRequest.class);
        kryo.register(GetUserGameHistoryRequest.class);
        kryo.register(UpdateInvitationRequest.class);
        kryo.register(UpdateSessionExpirationRequest.class);
    }

    /**
     *
     */
    static class Session {
        String accessToken;

        protected Session(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    /**
     *
     */
    public static class UpdateSessionExpirationRequest {
        String accessToken;

        public UpdateSessionExpirationRequest(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    /**
     *
     */
    public static class LoginRequest {
        String email;
        String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    /**
     *
     */
    public static class LogoutRequest extends Session {
        public LogoutRequest(String accessToken) {
            super(accessToken);
        }
    }

    /**
     *
     */
    public static class RegisterRequest {
        String password;
        String email;
        String nickName;
        String nameFirst;
        String nameLast;

        public RegisterRequest(String password, String email, String nickName, String nameFirst, String nameLast) {
            this.password = password;
            this.email = email;
            this.nickName = nickName;
            this.nameFirst = nameFirst;
            this.nameLast = nameLast;
        }
    }

    /**
     *
     */
    public static class GetGameRequest extends Session {
        int gameID;

        public GetGameRequest(String accessToken, int gameID) {
            super(accessToken);
            this.gameID = gameID;
        }
    }

    /**
     *
     */
    public static class GetPieceLocationRequest extends Session {
        int pieceID;

        public GetPieceLocationRequest(String accessToken, int pieceID) {
            super(accessToken);
            this.pieceID = pieceID;
        }
    }

    /**
     *
     */
    public static class UpdatePieceLocationRequest extends Session {
        int pieceID;
        int x;
        int y;

        public UpdatePieceLocationRequest(String accessToken, int pieceID, int x, int y) {
            super(accessToken);
            this.pieceID = pieceID;
            this.x = x;
            this.y = y;
        }
    }

    /**
     *
     */
    public static class GetPlayerRequest extends Session {
        int playerID;

        public GetPlayerRequest(String accessToken, int playerID) {
            super(accessToken);
            this.playerID = playerID;
        }
    }

    /**
     *
     */
    public static class GetUserRequest extends Session {
        int userID;

        public GetUserRequest(String accessToken, int userID) {
            super(accessToken);
            this.userID = userID;
        }
    }

    /**
     *
     */
    public static class GetUserGameHistoryRequest extends Session {
        int userID;

        public GetUserGameHistoryRequest(String accessToken, int userID) {
            super(accessToken);
            this.userID = userID;
        }
    }

    /**
     *
     */
    public static class UpdateInvitationRequest extends Session {
        int invitationID;
        Types.InvitationResponseType responseType;

        public UpdateInvitationRequest(String accessToken, int invitationID, Types.InvitationResponseType responseType) {
            super(accessToken);
            this.invitationID = invitationID;
            this.responseType = responseType;
        }
    }
}