package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetGameRequest extends Session {
    private int gameID;

    public GetGameRequest(String accessToken, int gameID) {
        super(accessToken);
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
