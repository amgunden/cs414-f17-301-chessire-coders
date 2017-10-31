package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;


import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerStatus;

public class GetGameResponse extends Response {
    private int gameID;
    private int playerOneID;
    private int playerTwoID;
    private PlayerStatus playerTwoStatus;
    private GameStatus status;

    public GetGameResponse() {
    }

    public GetGameResponse(int statusCode, String message) {
        super(statusCode, message);
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayerOneID() {
        return playerOneID;
    }

    public void setPlayerOneID(int playerOneID) {
        this.playerOneID = playerOneID;
    }

    public int getPlayerTwoID() {
        return playerTwoID;
    }

    public void setPlayerTwoID(int playerTwoID) {
        this.playerTwoID = playerTwoID;
    }

    public PlayerStatus getPlayerTwoStatus() {
        return playerTwoStatus;
    }

    public void setPlayerTwoStatus(PlayerStatus playerTwoStatus) {
        this.playerTwoStatus = playerTwoStatus;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GetGameResponse(int gameID, int playerOneID, int playerTwoID, PlayerStatus playerTwoStatus, GameStatus status) {
        this.gameID = gameID;
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;
        this.playerTwoStatus = playerTwoStatus;
        this.status = status;
    }
}
