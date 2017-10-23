package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Types {

    public static void registerTypes(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ErrorType.class);
        kryo.register(GameOutcomeType.class);
        kryo.register(GameStatus.class);
        kryo.register(InvitationResponseType.class);
        kryo.register(LoginStatus.class);
        kryo.register(PlayerColor.class);
        kryo.register(PlayerStatus.class);
        kryo.register(ServerEventType.class);
    }
}
