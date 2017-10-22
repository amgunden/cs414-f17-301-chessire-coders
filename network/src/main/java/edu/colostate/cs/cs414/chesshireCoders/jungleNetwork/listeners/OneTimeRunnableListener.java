package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

/**
 * This class can be used to handle responses from the server without blocking the thread that sends and receives
 * messages from the server. It will also remove itself from the EndPoint after the Runnable has been invoked.
 *
 * @param <T> The type of object to listen for.
 */
public class OneTimeRunnableListener<T> extends Listener {

    private final Class<T> c;
    private final Runnable r;
    private final EndPoint e;

    /**
     * Constructs the listener.
     *
     * @param c The class type to listen for
     * @param r A runnable object to be called if received is called with an object of type T
     * @param e The client/server to remove this listener from.
     */
    public OneTimeRunnableListener(Class<T> c, EndPoint e, Runnable r) {
        this.c = c;
        this.r = r;
        this.e = e;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (c.isInstance(object)) {
            Thread thread = new Thread(r);
            thread.start();
            e.removeListener(this);
        }
    }
}
