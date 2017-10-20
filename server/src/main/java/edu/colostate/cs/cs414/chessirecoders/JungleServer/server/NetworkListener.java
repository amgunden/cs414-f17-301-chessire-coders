package edu.colostate.cs.cs414.chessireCoders.jungleServer.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.*;

public class NetworkListener {

    /**
     * Creates and adds all listeners required for the server. See ClientRequests.java for more information about each object type.
     */
    public static void addListeners(Server server) {

        // LoginRequest Listener
        server.addListener(new FilteredListener<LoginRequest>(LoginRequest.class) {
            @Override
            public void run(Connection connection, LoginRequest received) {

            }
        });

        // LogoutRequest Listener
        server.addListener(new FilteredListener<LogoutRequest>(LogoutRequest.class) {
            @Override
            public void run(Connection connection, LogoutRequest received) {

            }
        });

        // RegisterRequest Listener
        server.addListener(new FilteredListener<RegisterRequest>(RegisterRequest.class) {
            @Override
            public void run(Connection connection, RegisterRequest received) {

            }
        });

        // GetGameRequest Listener
        server.addListener(new FilteredListener<GetGameRequest>(GetGameRequest.class) {
            @Override
            public void run(Connection connection, GetGameRequest received) {

            }
        });

        // GetPieceLocationRequestListener
        server.addListener(new FilteredListener<GetPieceLocationRequest>(GetPieceLocationRequest.class) {
            @Override
            public void run(Connection connection, GetPieceLocationRequest received) {

            }
        });

        // UpdatePieceLocationRequest Listener
        server.addListener(new FilteredListener<UpdatePieceLocationRequest>(UpdatePieceLocationRequest.class) {
            @Override
            public void run(Connection connection, UpdatePieceLocationRequest received) {

            }
        });

        // GetPlayerRequest Listener
        server.addListener(new FilteredListener<GetPlayerRequest>(GetPlayerRequest.class) {
            @Override
            public void run(Connection connection, GetPlayerRequest received) {

            }
        });

        // GetUserRequest Listener
        server.addListener(new FilteredListener<GetUserRequest>(GetUserRequest.class) {
            @Override
            public void run(Connection connection, GetUserRequest received) {

            }
        });

        // UpdateInvitationRequest Listener
        server.addListener(new FilteredListener<UpdateInvitationRequest>(UpdateInvitationRequest.class) {
            @Override
            public void run(Connection connection, UpdateInvitationRequest received) {

            }
        });

        // UpdateSessionExpirationRequest Listener
        server.addListener(new FilteredListener<UpdateSessionExpirationRequest>(UpdateSessionExpirationRequest.class) {
            @Override
            public void run(Connection connection, UpdateSessionExpirationRequest received) {

            }
        });
    }
}
