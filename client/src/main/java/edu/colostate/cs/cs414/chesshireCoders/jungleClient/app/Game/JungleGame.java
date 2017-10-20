package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

import java.util.Date;

public class JungleGame {
	int GameID = 0;
	Date startDateTime = new Date();
	Date endDateTime;
	int Status = 0;
	//Status could be a String or an int with value of three (0,1,2) or (1,2,3) signifying each case. 
	
	public JungleGame(int userIdOne, int userIdTwo) {
		
	}

	public static void main(String[] args) {
	}
	
	/*
	public void move() {
		//The Most important/common method. 
		//This function has been broken down into the ones above.
	}
	*/
	
	public boolean checkMove() {
		//This function should check the ability of the current peice to move in the requested way. Returning true iff it is completely possible. 
		return true;
	}
	
	public boolean commitMove() {
		//This function should submit the move to the system, it should be called post checkMove but for now check Move is called preemptively. 
		//Returns true iff the move is valid and the database accepts it. 
		if(!checkMove()) {
			return false;
		}
		
		return true;
	}
	
	public void sendInvitation() {
		//Not sure this is needed but I'm working on getting ideas down currently.
		//If used this method should accept a username. It should then send a invitation to that user that is only valid for this game. 
	}
	
	public void quitGame() {
		//This method should remove the user requesting it, if the game is not over that user officially loses the game. 
	}
}
