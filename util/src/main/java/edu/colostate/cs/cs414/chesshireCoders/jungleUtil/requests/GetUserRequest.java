package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class GetUserRequest extends Session {
	
	private String email;
	
    public GetUserRequest() {
    }
    
    public GetUserRequest(AuthToken token, String email) {
    	setAuthToken(token);
    	setEmail(email);
    }
    
    @Override
    public AuthToken getAuthToken() {
    	return super.getAuthToken();
    }
    
    @Override
    public GetUserRequest setAuthToken(AuthToken authToken) {
    	return (GetUserRequest) super.setAuthToken(authToken);
    }
    
    public String getEmail() {
    	return email;
    }
    
    public GetUserRequest setEmail(String email) {
    	this.email=email;
    	return this;
    }
}
