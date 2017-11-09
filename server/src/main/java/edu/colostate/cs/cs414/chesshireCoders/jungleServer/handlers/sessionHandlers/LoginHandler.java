package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers.sessionHandlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.sql.SQLException;

public class LoginHandler extends Listener {

    private JungleDB jungleDB = JungleDB.getInstance();
    private LoginManager loginManager = LoginManager.getManager();

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof LoginRequest) {
            try {
                connection.sendTCP(handleLogin((LoginRequest) received, connection));
            } catch (SQLException e) {
                connection.sendTCP(new LoginResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        }
    }

    private LoginResponse handleLogin(LoginRequest request, Connection connection) throws SQLException {
        try {
            boolean authorized = loginManager.authenticate(
                    request.getEmail(),
                    request.getPassword(),
                    connection
            );

            AuthToken token = loginManager.getAuthToken(connection);

            if (authorized) {
                return new LoginResponse(token);
            } else {
                return new LoginResponse(ResponseStatusCodes.UNAUTHORIZED, "Login failed. Incorrect username or password.");
            }
        } catch (Exception e) {
            return new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());
        }
    }
}
