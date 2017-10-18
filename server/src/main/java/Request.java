import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Request {

    public static void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Login.class);
        kryo.register(Logout.class);
        kryo.register(Register.class);
        kryo.register(GetGame.class);
        kryo.register(GetPieceLocation.class);
        kryo.register(UpdatePieceLocation.class);
        kryo.register(GetPlayer.class);
        kryo.register(GetUser.class);
        kryo.register(GetUserGameHistory.class);
    }

    public static class Session {
        public byte[] sessionToken;
        public long expiresAt;      // expressed as milliseconds since epoch time.
    }

    public static class RenewSession {
        public byte[] sessionToken;
    }
    
    public static class Login {
        public String email;
        public String password;
    }

    public static class Logout extends Session {
        public String email;
    }

    public static class Register {
        public String password;
        public String email;
        public String nickName;
        public String nameFirst;
        public String nameLast;
    }

    public static class GetGame extends Session {
        public int gameID;
    }

    public static class GetPieceLocation extends Session {
        public int pieceID;
    }

    public static class UpdatePieceLocation extends Session {
        public int pieceID;
        public int x;
        public int y;
    }

    public static class GetPlayer extends Session {
        public int playerID;
    }

    public static class GetUser extends Session {
        public int userID;
    }

    public static class GetUserGameHistory extends Session {
        public int userID;
    }

    public static class RespondToInvitation {
        public int invitationID;
        public Types.InvitationResponseType responseType;
    }

}
