package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetGameRequest extends SessionRequest {

    private int gameID;

    public GetGameRequest() {
    }

    public int getGameID() {
        return gameID;
    }

    public GetGameRequest setGameID(int gameID) {
        this.gameID = gameID;
        return this;
    }
}
