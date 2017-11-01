package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateSessionExpirationRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LogoutResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UpdateSessionExpirationResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginVerifier;

import java.sql.SQLException;

public class SessionHandler extends AbstractRequestHandler {

    private JungleDB jungleDB = JungleDB.getInstance();
    private LoginManager manager = new LoginManager();

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
                        connection.sendTCP(handleUpdateSessionExpiration(connection, received));
                    }
                }));
    }

    private UpdateSessionExpirationResponse handleUpdateSessionExpiration(Connection connection, UpdateSessionExpirationRequest received) {
        if (manager.isLoggedIn(connection) && !manager.isSessionExpired(connection)) {
            return new UpdateSessionExpirationResponse(-1L);
        } else {
            return new UpdateSessionExpirationResponse(ResponseStatusCodes.CLIENT_ERROR, "Session is expired");
        }
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
