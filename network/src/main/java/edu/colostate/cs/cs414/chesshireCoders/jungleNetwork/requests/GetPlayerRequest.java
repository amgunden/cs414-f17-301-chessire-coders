package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class GetPlayerRequest extends Session {
    int playerID;

    public GetPlayerRequest(String accessToken, int playerID) {
        super(accessToken);
        this.playerID = playerID;
    }
}
