package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class CreateGameResponse extends Response {

	private int gameID = -1;

	public CreateGameResponse(int statusCode, String errMsg, int gameID) {
		super(statusCode, errMsg);
		setGameID(gameID);
	}

    public int getGameId() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
	
}
