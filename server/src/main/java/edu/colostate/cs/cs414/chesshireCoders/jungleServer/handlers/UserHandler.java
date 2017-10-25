package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import static edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.ResponseStatusCodes.SERVER_ERROR;

import java.sql.SQLException;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.GetUserResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;

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
            	GetUserResponse response = new GetUserResponse();
            	response.setUserID(user.getUserId());
            	response.setFirstName(user.getNameFirst());
            	response.setLastName(user.getNameLast());
            	response.setNickName(user.getNickName());
                return response;

            } finally {
                userDAO.closeConnection();
            }
        } catch (SQLException e) {
            return new GetUserResponse(SERVER_ERROR, e.getMessage());
        }  	
    }

}
