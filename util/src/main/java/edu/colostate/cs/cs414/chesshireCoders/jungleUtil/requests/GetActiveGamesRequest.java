package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class GetActiveGamesRequest extends SessionRequest {

    public GetActiveGamesRequest() {
    }
    
    public GetActiveGamesRequest(AuthToken authToken) {
		super(authToken);
	}
}
