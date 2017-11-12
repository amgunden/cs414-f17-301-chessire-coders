package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

import java.util.Date;
import java.util.List;

public class Game {

    private long gameID;
    private Date gameStart;
    private Date gameEnd;
    private long playerOneID;
    private long playerTwoID;
    private GameStatus gameStatus;
    private List<GamePiece> gamePieces;

    public Game() {
    }

    public long getGameID() {
        return gameID;
    }

    public Game setGameID(long gameID) {
        this.gameID = gameID;
        return this;
    }

    public Date getGameStart() {
        return gameStart;
    }

    public Game setGameStart(Date gameStart) {
        this.gameStart = gameStart;
        return this;
    }

    public Date getGameEnd() {
        return gameEnd;
    }

    public Game setGameEnd(Date gameEnd) {
        this.gameEnd = gameEnd;
        return this;
    }

    public long getPlayerOneID() {
        return playerOneID;
    }

    public Game setPlayerOneID(long playerOneID) {
        this.playerOneID = playerOneID;
        return this;
    }

    public long getPlayerTwoID() {
        return playerTwoID;
    }

    public Game setPlayerTwoID(long playerTwoID) {
        this.playerTwoID = playerTwoID;
        return this;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Game setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        return this;
    }

    public List<GamePiece> getGamePieces() {
        return gamePieces;
    }

    public Game setGamePieces(List<GamePiece> gamePieces) {
        this.gamePieces = gamePieces;
        return this;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Game game = (Game) object;

        if (getGameID() != game.getGameID()) return false;
        if (getPlayerOneID() != game.getPlayerOneID()) return false;
        if (getPlayerTwoID() != game.getPlayerTwoID()) return false;
        if (getGameStart() != null ? !getGameStart().equals(game.getGameStart()) : game.getGameStart() != null)
            return false;
        if (getGameEnd() != null ? !getGameEnd().equals(game.getGameEnd()) : game.getGameEnd() != null) return false;
        if (getGameStatus() != game.getGameStatus()) return false;
        return getGamePieces() != null ? getGamePieces().equals(game.getGamePieces()) : game.getGamePieces() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getGameID() ^ (getGameID() >>> 32));
        result = 31 * result + (getGameStart() != null ? getGameStart().hashCode() : 0);
        result = 31 * result + (getGameEnd() != null ? getGameEnd().hashCode() : 0);
        result = 31 * result + (int) (getPlayerOneID() ^ (getPlayerOneID() >>> 32));
        result = 31 * result + (int) (getPlayerTwoID() ^ (getPlayerTwoID() >>> 32));
        result = 31 * result + (getGameStatus() != null ? getGameStatus().hashCode() : 0);
        result = 31 * result + (getGamePieces() != null ? getGamePieces().hashCode() : 0);
        return result;
    }
}
