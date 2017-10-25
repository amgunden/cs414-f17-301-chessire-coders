package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.UpdateSessionExpirationRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.LogoutResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginVerifier;

import java.sql.SQLException;

public class SessionHandler extends AbstractRequestHandler {

    LoginManager manager = new LoginManager();

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
                        try {
                            connection.sendTCP(handleLoginRequest(connection, received));
                        } catch (SQLException e) {
                            connection.sendTCP(new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage()));
                        }
                    }
                }));

        // LogoutRequest Listener
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<LogoutRequest>(LogoutRequest.class) {
                    @Override
                    public void run(Connection connection, LogoutRequest received) {
                        connection.sendTCP(handleLogout(connection, received));
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

    private LogoutResponse handleLogout(Connection connection, LogoutRequest request) {
        manager.logoutUser(connection);
        return new LogoutResponse(ResponseStatusCodes.SUCCESS, "Success");
    }

    private LoginResponse handleLoginRequest(Connection connection, LoginRequest request) throws SQLException {
        LoginVerifier verifier = new LoginVerifier(request.getEmail(), request.getPassword());

        // Check if registered
        if (verifier.isEmailRegistered()) {
            // Check if password okay
            if (verifier.isPasswordOkay()) {
                // Log user in
                manager.loginUser(connection, verifier.getUserInfo());
                return new LoginResponse(
                        ResponseStatusCodes.SUCCESS,
                        "Login successful."); // TODO: set expiration, and session token.
            } else {
                // Handle bad password
                return new LoginResponse(
                        ResponseStatusCodes.CLIENT_ERROR,
                        "Bad Password.");
            }
        } else {
            // handle bad email
            return new LoginResponse(
                    ResponseStatusCodes.CLIENT_ERROR,
                    "Email is not registered");
        }
    }

}
