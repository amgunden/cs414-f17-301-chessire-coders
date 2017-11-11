package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import java.sql.SQLException;

public class SessionHandler extends Listener {

    private HikariConnectionProvider hikariConnectionProvider = HikariConnectionProvider.getInstance();
    private SessionService sessionService = new SessionServiceImpl();

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof LoginRequest) {
            connection.sendTCP(handleLogin((LoginRequest) received, connection));
        } else if (received instanceof LogoutRequest) {
            handleLogout((LogoutRequest) received, connection);
        }
    }

    private void handleLogout(LogoutRequest received, Connection connection) {
        try {
            sessionService.expireSession(JungleConnection
                    .class
                    .cast(connection)
                    .getAuthToken()
                    .getToken()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LoginResponse handleLogin(LoginRequest request, Connection connection) {
        try {
            AuthToken token = sessionService.authenticate(
                    request.getEmail(),
                    request.getPassword(),
                    connection
            );
            return new LoginResponse()
                    .setAuthToken(token);
        } catch (CredentialException e) {
            return new LoginResponse(ResponseStatusCodes.UNAUTHORIZED, e.getMessage());
        } catch (AccountNotFoundException | AccountLockedException e) {
            return new LoginResponse(ResponseStatusCodes.CLIENT_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());
        }
    }
}
