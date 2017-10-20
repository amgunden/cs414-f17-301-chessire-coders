package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chessirecoders.JungleServer.data.Types;

public class GetPlayerResponse {
    int playerID;
    String playerName;
    Types.PlayerColor playerColor;

    public GetPlayerResponse(int playerID, String playerName, Types.PlayerColor playerColor) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerColor = playerColor;
    }
}
