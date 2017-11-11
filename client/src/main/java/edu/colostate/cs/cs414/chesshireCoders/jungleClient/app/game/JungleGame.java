package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerStatus;

public class JungleGame extends Game {
	protected GameBoard board;
	
	public JungleGame(int gameID) {
		setGameID(gameID);
		board = new GameBoard();
	}
	
	public JungleGame(int gameID, long gameStart, long gameEnd, int playerOneID, int playerTwoID,
			PlayerStatus playerTwoStatus, GameStatus gameStatus) {
		super(gameID, gameStart, gameEnd, playerOneID, playerTwoID, playerTwoStatus, gameStatus);
		board = new GameBoard();
	}

	public JungleGame(long gameStart, long gameEnd, int playerOneID, GameStatus gameStatus) {
		super(gameStart, gameEnd, playerOneID, gameStatus);
		board = new GameBoard();
	}

	public JungleGame(long gameStart, long gameEnd, int playerOneID, int playerTwoID, PlayerStatus playerTwoStatus,
			GameStatus gameStatus) {
		super(gameStart, gameEnd, playerOneID, playerTwoID, playerTwoStatus, gameStatus);
		board = new GameBoard();
	}

	public boolean canMovePieceAt(int row, int column)
	{
		boolean result = false;
		
		// TODO check that it is this computer's turn
		if (board.getPieceAt(row, column) != null) {
			if (board.getPieceAt(row, column).getColor() == PlayerColor.Red) {
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
	
	public void quitGame(Player actingPlayer) {
		//This method should remove the user requesting it, if the game is not over that user officially loses the game. 
	}
	
	@Override
	public String toString() {
		return Integer.toString(getGameID());
	}
}
