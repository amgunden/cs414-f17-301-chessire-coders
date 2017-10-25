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
	
	public JungleGame(int userIdOne) {
		
	}
	
	public JungleGame(int userIdOne, int userIdTwo) {
		
	}
	
	public void sendInvitation() {
		//Not sure this is needed but I'm working on getting ideas down currently.
		//If used this method should accept a username. It should then send a invitation to that user that is only valid for this game. 
	}
	
	public boolean startGame() {
		return true;
	}	
	
	public int[] getValidMoves(GamePiece piece) {
		return null;		
	}
	
	public boolean movePiece(GamePiece piece) {
		return true;		
	}
	
	public void endGame() {

	}	
	
	public boolean hasWinner() {
		return false;
	}	
	
	public Player getWinner() {
		return player1;
	}
	
	public void quitGame(Player actingPlayer) {
		//This method should remove the user requesting it, if the game is not over that user officially loses the game. 
	}
}
