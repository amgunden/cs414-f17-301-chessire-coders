package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * This class can be used to handle responses from the server without blocking the thread that sends and receives
 * messages from the server.
 *
 * @param <T> The type of object to listen for.
 */
public class RunnableFilteredListener<T> extends Listener {

    private final Class<T> c;
    private final Runnable r;

    /**
     * Constructs the listener.
     *
     * @param c The class type to listen for
     * @param r A runnable object to be called if received is called with an object of type T
     */
    public RunnableFilteredListener(Class<T> c, Runnable r) {
        this.c = c;
        this.r = r;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (c.isInstance(object)) {
            Thread thread = new Thread(r);
            thread.start();
        }
    }
}
