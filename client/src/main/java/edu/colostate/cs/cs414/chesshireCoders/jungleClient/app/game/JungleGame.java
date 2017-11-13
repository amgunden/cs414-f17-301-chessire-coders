package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class JungleGame extends Game {

    protected GameBoard board;
    protected PlayerEnumType viewingPlayer;

    public JungleGame() {
        super();
    }

    public JungleGame(int gameID) {
        super();
        setGameID(gameID);
        board = new GameBoard();
    }

    public JungleGame(Game game) {
		super();
		setGameEnd(game.getGameEnd());
		setGameID(game.getGameID());
		setGamePieces(game.getGamePieces());
		setGameStart(game.getGameStart());
		setGameStatus(game.getGameStatus());
		setPlayerOneID(game.getPlayerOneID());
		setPlayerTwoID(game.getPlayerTwoID());
		setTurnOfPlayer(game.getTurnOfPlayer());
	}

	public boolean canMovePieceAt(int row, int column) {
        boolean result = false;

        if (this.getTurnOfPlayer() == viewingPlayer && board.getPieceAt(row, column) != null) {
            if (board.getPieceAt(row, column).getPlayerOwner() == viewingPlayer) {
                result = true;
            }
        }

        return result;
    }

    public boolean startGame() {
        return true;
    }

    public int[] getValidMoves(int row, int column) {
        return board.getValidMoves(row, column);
    }

    public void movePiece(int[] from, int[] to) {
        board.movePiece(from, to);
    }

    public void endGame() {

    }

    public boolean hasWinner() {
        return false;
    }

    public PlayerColor getWinner() {
        return board.getWinner();
    }

    public PlayerEnumType getViewingPlayer() {
		return viewingPlayer;
	}

	public void setViewingPlayer(PlayerEnumType viewingPlayer) {
		this.viewingPlayer = viewingPlayer;
	}

	public void quitGame(Player actingPlayer) {
        //This method should remove the user requesting it, if the game is not over that user officially loses the game.
    }

    @Override
    public String toString() {
        return Long.toString(getGameID());
    }
}
