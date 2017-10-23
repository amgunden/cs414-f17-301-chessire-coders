package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PlayerStatus;

import java.sql.Timestamp;

public class Game {

    private int gameID;
    private Timestamp gameStart;
    private Timestamp gameEnd;
    private int playerOneID;
    private int playerTwoID;
    private PlayerStatus playerTwoStatus;
    private GameStatus gameStatus;

    public Game() {}

    public Game(int gameID, Timestamp gameStart, Timestamp gameEnd, int playerOneID, int playerTwoID, PlayerStatus playerTwoStatus, GameStatus gameStatus) {
        this.gameID = gameID;
        this.gameStart = gameStart;
        this.gameEnd = gameEnd;
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;
        this.playerTwoStatus = playerTwoStatus;
        this.gameStatus = gameStatus;
    }

    public Game(Timestamp gameStart, Timestamp gameEnd, int playerOneID, int playerTwoID, PlayerStatus playerTwoStatus, GameStatus gameStatus) {
        this.gameStart = gameStart;
        this.gameEnd = gameEnd;
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;
        this.playerTwoStatus = playerTwoStatus;
        this.gameStatus = gameStatus;
    }

    public Game(Timestamp gameStart, Timestamp gameEnd, int playerOneID, GameStatus gameStatus) {
        this.gameStart = gameStart;
        this.gameEnd = gameEnd;
        this.playerOneID = playerOneID;
        this.gameStatus = gameStatus;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Timestamp getGameStart() {
        return gameStart;
    }

    public void setGameStart(Timestamp gameStart) {
        this.gameStart = gameStart;
    }

    public Timestamp getGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(Timestamp gameEnd) {
        this.gameEnd = gameEnd;
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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
