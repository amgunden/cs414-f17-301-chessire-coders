package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginVerifier;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateSessionExpirationRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LogoutResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UpdateSessionExpirationResponse;

import java.sql.SQLException;

public class SessionHandler extends Listener {

    private LoginManager manager = new LoginManager();

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof LoginRequest) {
            try {
                connection.sendTCP(handleLoginRequest(connection, (LoginRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage()));
            }

        } else if (received instanceof LogoutRequest) {
            connection.sendTCP(handleLogout(connection, (LogoutRequest) received));

        } else if (received instanceof UpdateSessionExpirationRequest) {
            connection.sendTCP(handleUpdateSessionExpiration(connection, (UpdateSessionExpirationRequest) received));

        }
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
