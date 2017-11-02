package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;
import java.util.Date;

public class JungleGame {
	int GameID = 0;
	Date startDateTime = new Date();
	Date endDateTime;
	int Status = 0;
	Player player1;
	Player player2;
	boolean liveGame = false;
	private GameBoard board;
	
	public JungleGame(int userIdOne) {
		board = new GameBoard();
	}
	
	public JungleGame(int userIdOne, int userIdTwo) {
		board = new GameBoard();
	}
	
	public void sendInvitation() {
		//Not sure this is needed but I'm working on getting ideas down currently.
		//If used this method should accept a username. It should then send a invitation to that user that is only valid for this game. 
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
}
