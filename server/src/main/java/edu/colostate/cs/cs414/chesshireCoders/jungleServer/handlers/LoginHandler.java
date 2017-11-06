package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;

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

        LoginDAO loginDAO = new LoginDAO(connection);
		
		
        try {
            
            // get UserID from provided email
            Login login = loginDAO.getLoginByEmail(request.getEmail());
            
            // compare hash in the DB to the one sent by the client
            if(request.getPassword().equals(login.getHashedPass())) {
            	
            	// generate new token and pass it into the Login Response constructor
            	String token = Crypto.generateAuthToken();
            	LoginResponse repsonse = new LoginResponse(login, token);
            	
            	return repsonse;
            	
            }
            else {
            	return new LoginResponse(ResponseStatusCodes.UNAUTHORIZED, "Login failed. Incorrect username or password.");
            }
            
        } catch (SQLException e) {

            // TODO craft error based on error code.
            return new LoginResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());

        } finally {

            connection.close();
        }
		
	}

}
