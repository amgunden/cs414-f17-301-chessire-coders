package edu.colostate.cs.cs414.chessireCoders.jungleNetwork.requests;

public class GetGameRequest extends Session {
    int gameID;

    public GetGameRequest(String accessToken, int gameID) {
        super(accessToken);
        this.gameID = gameID;
    }
}
