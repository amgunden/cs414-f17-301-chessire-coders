package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chessirecoders.JungleServer.data.Types;

public class GetGameResponse {
    int gameID;
    int playerOneID;
    int playerTwoID;
    Types.PlayerStatus playerTwoStatus;
    Types.GameStatus status;

    public GetGameResponse(int gameID, int playerOneID, int playerTwoID, Types.PlayerStatus playerTwoStatus, Types.GameStatus status) {
        this.gameID = gameID;
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;
        this.playerTwoStatus = playerTwoStatus;
        this.status = status;
    }
}
