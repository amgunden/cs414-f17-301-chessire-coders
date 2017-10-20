package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.responses;


import edu.colostate.cs.cs414.chessirecoders.jungleNetwork.types.PlayerColor;

public class GetPlayerResponse {
    int playerID;
    String playerName;
    PlayerColor playerColor;

    public GetPlayerResponse(int playerID, String playerName, PlayerColor playerColor) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }
}
