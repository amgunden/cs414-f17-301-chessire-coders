package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners;

import com.esotericsoftware.kryonet.Connection;

/**
 * This is a generic listener class that will only listen for objects of type T.
 *
 * @param <T> The object type to listen for
 */
public abstract class FilteredListener<T> extends com.esotericsoftware.kryonet.Listener {

    private final Class<T> c;

    public FilteredListener(Class<T> c) {
        this.c = c;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (c.isInstance(object)) {
            run(connection, c.cast(object));
        }
    }

    public abstract void run(Connection connection, T received);
}