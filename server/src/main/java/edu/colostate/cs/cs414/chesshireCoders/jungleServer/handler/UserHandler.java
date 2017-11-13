package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;


import java.sql.SQLException;

public class UserHandler extends Listener {

	private final JungleServer server;
	private RegistrationService registrationService = new RegistrationServiceImpl();

	public UserHandler(JungleServer server) {
		this.server = server;
	}

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof GetUserRequest) {
        	connection.sendTCP(handleGetUser((GetUserRequest) received));
        }
    }

    private GetUserResponse handleGetUser(final GetUserRequest request) {
    	try {
    		return new GetUserResponse(registrationService
    				.fetchUserByNickName(request.getNickName()));
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return new GetUserResponse(ResponseStatusCodes.SERVER_ERROR, "Error fetching user info.");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new GetUserResponse(ResponseStatusCodes.SERVER_ERROR, "Error fetching user info.");
    	}
    }
    
}
