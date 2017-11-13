package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class InviteReplyResponse extends Response {

	private long gameID;
	
	public InviteReplyResponse() {
		
	}
	
    public InviteReplyResponse(long gameID) {
    	super();
    	setGameID(gameID);
    }

    public InviteReplyResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

	public long getGameID() {
		return gameID;
	}

	public void setGameID(long gameID) {
		this.gameID = gameID;
	}
}
