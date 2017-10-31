package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Events {
    public static void kryoRegisterEvents(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(InvitationEvent.class);
        kryo.register(GameEndedEvent.class);
        kryo.register(ServerEvent.class);
    }
}
