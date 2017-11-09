package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetPlayerRequest extends Session {

    public GetPlayerRequest() {
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    private int playerID;

    public GetPlayerRequest(String accessToken, int playerID) {
        super(accessToken);
        this.playerID = playerID;
    }
}
