package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class RegistrationHandler extends Listener {

    private JungleServer server;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();

    public RegistrationHandler(JungleServer server) {
        this.server = server;
    }

    public RegistrationHandler() {

    }

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof RegisterRequest) {
            connection.sendTCP(handleNewRegistration((RegisterRequest) received, connection));
        } else if (received instanceof UnRegisterRequest) {
            connection.sendTCP(handleUnregisterRequest((UnRegisterRequest) received, connection));
        }
    }

    private RegisterResponse handleNewRegistration(RegisterRequest request, Connection connection) {
        try {
            registrationService.registerUser(request.getNickName(), request.getEmail(), request.getPassword());
            AuthToken authToken = sessionService.authenticate(request.getEmail(), request.getPassword(), connection);
            return new RegisterResponse()
                    .setAuthToken(authToken)
                    .setNickName(request.getNickName()); // Defaults to success
        } catch (Exception e) {
            e.printStackTrace();
            return new RegisterResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());
        }
    }

    private UnRegisterResponse handleUnregisterRequest(UnRegisterRequest request, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(request, connection)) {
                JungleConnection jungConn = JungleConnection.class.cast(connection);
                registrationService.unregisterUser(jungConn.getNickName());
                sessionService.expireSession(jungConn
                        .getAuthToken()
                        .getToken());
                return new UnRegisterResponse(); // Defaults to success
            } else
                return new UnRegisterResponse(ResponseStatusCodes.UNAUTHORIZED, "You are not authorized to perform this action.");
        } catch (Exception e) {
            e.printStackTrace();
            return new UnRegisterResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());
        }
    }
}
