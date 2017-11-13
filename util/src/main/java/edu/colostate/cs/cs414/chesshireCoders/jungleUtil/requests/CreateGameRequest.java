package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class CreateGameRequest extends SessionRequest {

	public CreateGameRequest() {
		super();
	}

	public CreateGameRequest(AuthToken authToken) {
		super(authToken);
	}

}
