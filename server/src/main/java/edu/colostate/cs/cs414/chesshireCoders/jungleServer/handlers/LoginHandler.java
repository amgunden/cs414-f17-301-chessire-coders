package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;

import com.esotericsoftware.kryonet.Connection;

import java.sql.SQLException;

public class LoginHandler extends Listener{
	
	private JungleDB jungleDB = JungleDB.getInstance();
	
	@Override
    public void received(Connection connection, Object received) {

        if (received instanceof LoginRequest) {
            try {
                connection.sendTCP(handleLogin((LoginRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new LoginResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        } 
    }
	
	private LoginResponse handleLogin(LoginRequest request) throws SQLException {
		
		java.sql.Connection connection = jungleDB.getConnection();
		UserDAO userDAO = new UserDAO(connection);
        LoginDAO loginDAO = new LoginDAO(connection);
		
		
        try {
            connection.setAutoCommit(false);
            // get UserID from provided email
            Login login = loginDAO.getLoginByEmail(request.getEmail());
            
            
            
            if(request.getPassword().equals(login.getHashedPass())) {
            	
            	String token = Crypto.generateAuthToken();
            	
            	LoginResponse repsonse = new LoginResponse(login, token);
            	
            	loginDAO.insert(login);
            	
            	return repsonse;
            	
            }
            else {
            	return new LoginResponse(ResponseStatusCodes.UNAUTHORIZED, "Login failed. Incorrect username or password.");
            }
            
        } catch (SQLException e) {

            connection.rollback();
            // TODO craft error based on error code.
            return new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());

        } finally {

            connection.setAutoCommit(true);
            connection.close();
        }
		
	}

}
