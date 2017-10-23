package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;


import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PlayerColor;

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
