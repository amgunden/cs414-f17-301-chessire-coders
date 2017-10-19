package edu.colostate.cs.cs414.chessirecoders.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class NetworkListener {

    Server server;

    public NetworkListener(Server server) {
        this.server = server;
        this.addListeners();
    }

    /**
     * Creates and adds all listeners required for the server. See ClientRequests.java for more information about each object type.
     */
    private void addListeners() {

        // LoginRequest Listener
        server.addListener(new FilteredListener<ClientRequest.LoginRequest>(ClientRequest.LoginRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.LoginRequest received) {

            }
        });

        // LogoutRequest Listener
        server.addListener(new FilteredListener<ClientRequest.LogoutRequest>(ClientRequest.LogoutRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.LogoutRequest received) {

            }
        });

        // RegisterRequest Listener
        server.addListener(new FilteredListener<ClientRequest.RegisterRequest>(ClientRequest.RegisterRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.RegisterRequest received) {

            }
        });

        // GetGameRequest Listener
        server.addListener(new FilteredListener<ClientRequest.GetGameRequest>(ClientRequest.GetGameRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.GetGameRequest received) {

            }
        });

        // GetPieceLocationRequestListener
        server.addListener(new FilteredListener<ClientRequest.GetPieceLocationRequest>(ClientRequest.GetPieceLocationRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.GetPieceLocationRequest received) {

            }
        });

        // UpdatePieceLocationRequest Listener
        server.addListener(new FilteredListener<ClientRequest.UpdatePieceLocationRequest>(ClientRequest.UpdatePieceLocationRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.UpdatePieceLocationRequest received) {

            }
        });

        // GetPlayerRequest Listener
        server.addListener(new FilteredListener<ClientRequest.GetPlayerRequest>(ClientRequest.GetPlayerRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.GetPlayerRequest received) {

            }
        });

        // GetUserRequest Listener
        server.addListener(new FilteredListener<ClientRequest.GetUserRequest>(ClientRequest.GetUserRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.GetUserRequest received) {

            }
        });

        // UpdateInvitationRequest Listener
        server.addListener(new FilteredListener<ClientRequest.UpdateInvitationRequest>(ClientRequest.UpdateInvitationRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.UpdateInvitationRequest received) {

            }
        });

        // UpdateSessionExpirationRequest Listener
        server.addListener(new FilteredListener<ClientRequest.UpdateSessionExpirationRequest>(ClientRequest.UpdateSessionExpirationRequest.class) {
            @Override
            public void run(Connection connection, ClientRequest.UpdateSessionExpirationRequest received) {

            }
        });
    }

    /**
     * This is a generic listener class that will only listen for objects of type T.
     *
     * @param <T> The object type to listen for
     */
    public static abstract class FilteredListener<T> extends com.esotericsoftware.kryonet.Listener {

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
}
