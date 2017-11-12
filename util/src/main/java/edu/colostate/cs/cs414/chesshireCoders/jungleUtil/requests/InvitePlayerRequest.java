package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class InvitePlayerRequest extends Session {

	private String nickname;
	private long gameID;
	
	public InvitePlayerRequest() {
		super();
	}

	public InvitePlayerRequest(AuthToken authToken, String nickname, long gameID) {
		super(authToken);
		setNickname(nickname);
		setGameID(gameID);
	}

	public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }
	
}
