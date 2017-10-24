package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.UpdateSessionExpirationRequest;

public class SessionHandler implements IRequestHandler {

    @Override
    public void addListeners(EndPoint endPoint) {
        // LoginRequest Listener
        endPoint.addListener(new Listener.ThreadedListener(
                new FilteredListener<LoginRequest>(LoginRequest.class) {
                    @Override
                    public void run(Connection connection, LoginRequest received) {
                        // TODO: delegate to login verifier

                    }
                }));

        // LogoutRequest Listener
        endPoint.addListener(new Listener.ThreadedListener(
                new FilteredListener<LogoutRequest>(LogoutRequest.class) {
                    @Override
                    public void run(Connection connection, LogoutRequest received) {
                        // TODO: delegate to login verifier
                    }
                }));

        // UpdateSessionExpirationRequest Listener
        endPoint.addListener(new Listener.ThreadedListener(
                new FilteredListener<UpdateSessionExpirationRequest>(UpdateSessionExpirationRequest.class) {
                    @Override
                    public void run(Connection connection, UpdateSessionExpirationRequest received) {
                        // TODO: handle a session expiration update request.
                    }
                }));
    }


}
