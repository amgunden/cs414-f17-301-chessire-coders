package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class GetAvailPlayersRequest extends SessionRequest {
	
	private String requestorNickname;

	public GetAvailPlayersRequest() {
		super();
	}
	
	public GetAvailPlayersRequest(AuthToken authToken, String nickName) {
		super(authToken);
		setRequestorNickname(nickName);
	}
	
	public String getRequestorNickname() {
		return requestorNickname;
	}

	public void setRequestorNickname(String requestorNickname) {
		this.requestorNickname = requestorNickname;
	}
	
	
}
