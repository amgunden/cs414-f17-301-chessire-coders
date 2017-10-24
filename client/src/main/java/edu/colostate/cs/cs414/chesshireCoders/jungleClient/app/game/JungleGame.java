package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import java.util.Date;

public class JungleGame {
	//NEED to find a datatype that will contain all the games if there are multiple. 
	int GameID = 0;
	Date startDateTime = new Date();
	Date endDateTime;
	int Status = 0;
	//Status could be a String or an int with value of three (0,1,2) or (1,2,3) signifying each case. 
	
	//This entire class is IN DEVELOPMENT. I need to figure out it's exact purpose and how we want it to handle the game vs games. How it will be passing and accepting Data. And more. 
	
	
	public JungleGame(int userIdOne, int userIdTwo) {
		
	}
	
	public void sendInvitation() {
		//Not sure this is needed but I'm working on getting ideas down currently.
		//If used this method should accept a username. It should then send a invitation to that user that is only valid for this game. 
	}
	
	public void quitGame() {
		//This method should remove the user requesting it, if the game is not over that user officially loses the game. 
	}
}
