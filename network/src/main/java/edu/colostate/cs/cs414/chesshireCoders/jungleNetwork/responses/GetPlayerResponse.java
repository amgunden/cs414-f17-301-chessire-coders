package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;


import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PlayerColor;

public class GetPlayerResponse {
    private int playerID;
    private String playerName;
    private PlayerColor playerColor;

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public GetPlayerResponse(int playerID, String playerName, PlayerColor playerColor) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }
}
