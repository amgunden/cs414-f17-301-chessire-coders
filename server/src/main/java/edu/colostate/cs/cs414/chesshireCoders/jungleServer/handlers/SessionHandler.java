package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.UpdateSessionExpirationRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;

public class SessionHandler extends AbstractRequestHandler {

    public SessionHandler(JungleServer server) {
        super(server);
    }

    @Override
    public void addListeners() {
        // LoginRequest Listener
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<LoginRequest>(LoginRequest.class) {
                    @Override
                    public void run(Connection connection, LoginRequest received) {
                        // TODO: delegate to login verifier

                    }
                }));

        // LogoutRequest Listener
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<LogoutRequest>(LogoutRequest.class) {
                    @Override
                    public void run(Connection connection, LogoutRequest received) {
                        // TODO: delegate to login verifier
                    }
                }));

        // UpdateSessionExpirationRequest Listener
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<UpdateSessionExpirationRequest>(UpdateSessionExpirationRequest.class) {
                    @Override
                    public void run(Connection connection, UpdateSessionExpirationRequest received) {
                        // TODO: handle a session expiration update request.
                    }
                }));
    }


}
