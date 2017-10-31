package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;

import java.sql.SQLException;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.SERVER_ERROR;

public class UserHandler extends AbstractRequestHandler{

	public UserHandler(JungleServer server) {
		super(server);
		// TODO Auto-generated constructor stub
	}

    @Override
    public void addListeners() {
        // GetUserRequest Handler
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<GetUserRequest>(GetUserRequest.class) {
                    @Override
                    public void run(Connection connection, GetUserRequest received) {
                        connection.sendTCP(handleGetUser(received));
                    }
                }));
    }
    
    private Response handleGetUser(GetUserRequest request) {
    	UserDAO userDAO = new UserDAO();
    	
    	try {
            try {
            	userDAO.getConnection();
            	User user = userDAO.getUserByUserId(request.getUserID());
                return new GetUserResponse(user);

            } finally {
                userDAO.closeConnection();
            }
        } catch (SQLException e) {
            return new GetUserResponse(SERVER_ERROR, e.getMessage());
        }  	
    }

}
