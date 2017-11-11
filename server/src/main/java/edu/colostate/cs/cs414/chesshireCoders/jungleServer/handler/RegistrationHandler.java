package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.ConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;

import java.sql.SQLException;

public class RegistrationHandler extends Listener {

    private ConnectionProvider provider = HikariConnectionProvider.getInstance();
    private DAOManager manager = DAOManager.instance(provider, PostgresDAOManager.class);
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof RegisterRequest) {
            try {
                connection.sendTCP(handleNewRegistration((RegisterRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new RegisterResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        } else if (received instanceof UnRegisterRequest) {
            try {
                connection.sendTCP(
                        handleUnregisterRequest((UnRegisterRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new UnRegisterResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        }
    }

    private RegisterResponse handleNewRegistration(RegisterRequest request) throws SQLException {
        try {
            registrationService.registerUser(request.getNickName(), request.getEmail(), request.getPassword());
            return new RegisterResponse(); // Defaults to success
        } catch (Exception e) {
            e.printStackTrace();
            return new RegisterResponse(ResponseStatusCodes.SERVER_ERROR, "Server Error: Unable to register user.");
        }
    }

    private UnRegisterResponse handleUnregisterRequest(UnRegisterRequest request) throws SQLException {
        try {
            registrationService.unregisterUser(request.getEmail());
            return new UnRegisterResponse(); // Defaults to success
        } catch (Exception e) {
            e.printStackTrace();
            return new UnRegisterResponse(ResponseStatusCodes.SERVER_ERROR, "Server Error: Unable to register user.");
        }
    }
}
