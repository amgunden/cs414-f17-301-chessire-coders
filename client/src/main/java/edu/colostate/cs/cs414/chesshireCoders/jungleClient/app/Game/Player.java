package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;


public class Player {
	PlayerColor colorOfPlayer = PlayerColor.Black;
	//I'm not sure what this value is supposed to be so I'm changing user to userID
	int userID = 0;

	public Player(PlayerColor color, int userID) {
		this.colorOfPlayer = color;
		this.userID = userID;
	}

}
