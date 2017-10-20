package edu.colostate.cs.cs414.chessireCoders.jungleNetwork.requests;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Requests {
    /**
     *
     * @param endPoint
     */
    public static void kryoRegisterRequests(EndPoint endPoint) {
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
}
