package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class GetUserGameHistoryRequest extends SessionRequest {

	private String nickname;
	
    public GetUserGameHistoryRequest() {
    }
    
    public GetUserGameHistoryRequest(AuthToken token, String nickname) {
    	setAuthToken(token);
    	setNickName(nickname);
    }
    
    @Override
    public GetUserGameHistoryRequest setAuthToken(AuthToken authToken) {
    	return (GetUserGameHistoryRequest) super.setAuthToken(authToken);
    }
    
    public String getNickName() {
    	return nickname;
    }
    
    public GetUserGameHistoryRequest setNickName(String nickname) {
    	this.nickname=nickname;
    	return this;
    }
}
