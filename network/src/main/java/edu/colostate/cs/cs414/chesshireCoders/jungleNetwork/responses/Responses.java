package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Responses {

    public static void kryoRegisterResponses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(GetGameResponse.class);
        kryo.register(GetPieceLocationResponse.class);
        kryo.register(GetPlayerResponse.class);
        kryo.register(GetUserGameHistoryResponse.class);
        kryo.register(GetInvitationResponse.class);
        kryo.register(LoginSuccessResponse.class);
        kryo.register(ErrorResponse.class);
    }
}
