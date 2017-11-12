package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class GetUserRequest extends Session {
	
	private String nickName;
	
    public GetUserRequest() {
    }
    
    public GetUserRequest(AuthToken token, String nick) {
    	setAuthToken(token);
    	setNickName(nick);
    }
    
    @Override
    public AuthToken getAuthToken() {
    	return super.getAuthToken();
    }
    
    @Override
    public GetUserRequest setAuthToken(AuthToken authToken) {
    	return (GetUserRequest) super.setAuthToken(authToken);
    }
    
    public String getNickName() {
    	return nickName;
    }
    
    public GetUserRequest setNickName(String nickName) {
    	this.nickName=nickName;
    	return this;
    }
}
