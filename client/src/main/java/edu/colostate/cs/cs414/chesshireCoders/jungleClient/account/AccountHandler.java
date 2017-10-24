package edu.colostate.cs.cs414.chesshireCoders.jungleClient.account;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.UnRegisterResponse;

public class AccountHandler {
	
	public boolean registerUser(String email, String nickname, String pw, String pwReentered) {
		boolean registrationStatus=false;
		String message="";
		RegisterRequest request = new RegisterRequest(pw, email, nickname, "nameFirst", "nameLast");
		RegisterResponse response = new RegisterResponse(registrationStatus, message );

		return registrationStatus;
	}
	
	
	// Thinking this method could return the User's ID if the login is successful
	public int validateLogin(String email, String pw){
		//from here call server actions
		return 1;
		
	}
	public boolean unregisterUser(String email, String pw) {
		boolean registrationStatus=false;
		String message="";
		UnRegisterRequest request = new UnRegisterRequest(pw, email);
		UnRegisterResponse response = new UnRegisterResponse(registrationStatus, message );

		return registrationStatus;
	}
}
