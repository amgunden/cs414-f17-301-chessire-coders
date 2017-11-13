package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class GetGameRequest extends SessionRequest {

    private long gameID;

    public GetGameRequest() {
    }
    
    public GetGameRequest(AuthToken authToken, long gameID) {
		super(authToken);
		setGameID(gameID);
	}

    public long getGameID() {
        return gameID;
    }

    public GetGameRequest setGameID(long gameID) {
        this.gameID = gameID;
        return this;
    }
}
