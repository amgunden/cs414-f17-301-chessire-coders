package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private long gameID;
    private Date gameStart;
    private Date gameEnd;
    private String playerOneNickName;
    private String playerTwoNickName;
    private GameStatus gameStatus;
    private List<GamePiece> gamePieces;
    private PlayerEnumType turnOfPlayer = PlayerEnumType.PLAYER_ONE;

    public Game() {
    }

    public Game(Game game) {
        this.gameID = game.gameID;
        this.gameStart = game.gameStart;
        this.gameEnd = game.gameEnd;
        this.playerOneNickName = game.playerOneNickName;
        this.playerTwoNickName = game.playerTwoNickName;
        this.gameStatus = game.gameStatus;
        this.gamePieces = new ArrayList<>(game.gamePieces);
        this.turnOfPlayer = game.turnOfPlayer;
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

    public String getPlayerOneNickName() {
        return playerOneNickName;
    }

    public Game setPlayerOneNickName(String playerOneNickName) {
        this.playerOneNickName = playerOneNickName;
        return this;
    }

    public String getPlayerTwoNickName() {
        return playerTwoNickName;
    }

    public Game setPlayerTwoNickName(String playerTwoNickName) {
        this.playerTwoNickName = playerTwoNickName;
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

    public PlayerEnumType getTurnOfPlayer() {
        return turnOfPlayer;
    }

    public Game setTurnOfPlayer(PlayerEnumType turnOfPlayer) {
        this.turnOfPlayer = turnOfPlayer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (getGameID() != game.getGameID()) return false;
        if (getGameStart() != null ? !getGameStart().equals(game.getGameStart()) : game.getGameStart() != null)
            return false;
        if (getGameEnd() != null ? !getGameEnd().equals(game.getGameEnd()) : game.getGameEnd() != null) return false;
        if (getPlayerOneNickName() != null ? !getPlayerOneNickName().equals(game.getPlayerOneNickName()) : game.getPlayerOneNickName() != null)
            return false;
        if (getPlayerTwoNickName() != null ? !getPlayerTwoNickName().equals(game.getPlayerTwoNickName()) : game.getPlayerTwoNickName() != null)
            return false;
        return getGameStatus() == game.getGameStatus();
    }

    @Override
    public int hashCode() {
        int result = (int) (getGameID() ^ (getGameID() >>> 32));
        result = 31 * result + (getGameStart() != null ? getGameStart().hashCode() : 0);
        result = 31 * result + (getGameEnd() != null ? getGameEnd().hashCode() : 0);
        result = 31 * result + (getPlayerOneNickName() != null ? getPlayerOneNickName().hashCode() : 0);
        result = 31 * result + (getPlayerTwoNickName() != null ? getPlayerTwoNickName().hashCode() : 0);
        result = 31 * result + (getGameStatus() != null ? getGameStatus().hashCode() : 0);
        return result;
    }
}
