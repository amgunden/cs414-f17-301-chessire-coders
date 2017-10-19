package edu.colostate.cs.cs414.chessirecoders.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public abstract class DynamicRecievedListener<T> extends Listener {

    private final Class<T> c;

    public DynamicRecievedListener(Class<T> c) {
        this.c = c;
    }

    @Override
    public void received(Connection connection, Object object) {
        if(c.isInstance(object)) {
            run(connection, c.cast(object));
        }
    }

    public abstract void run(Connection connection, T recieved);
}
