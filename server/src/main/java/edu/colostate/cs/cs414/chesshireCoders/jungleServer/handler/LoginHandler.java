package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import java.sql.SQLException;

public class LoginHandler extends Listener {

    private HikariConnectionProvider hikariConnectionProvider = HikariConnectionProvider.getInstance();
    private SessionService sessionService = new SessionServiceImpl();

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
            AuthToken token = sessionService.authenticate(
                    request.getEmail(),
                    request.getPassword(),
                    connection
            );
            return new LoginResponse()
                    .setAuthToken(token);
        } catch (CredentialException e) {
            return new LoginResponse(ResponseStatusCodes.UNAUTHORIZED, e.getMessage());
        } catch (AccountNotFoundException e) {
            return new LoginResponse(ResponseStatusCodes.CLIENT_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());
        }
    }
}
