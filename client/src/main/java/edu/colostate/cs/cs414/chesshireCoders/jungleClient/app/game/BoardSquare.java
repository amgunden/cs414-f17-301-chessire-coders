package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;


public class BoardSquare {
	SquareType typeOfSquare = SquareType.Normal;
	PlayerColor colorOfPlayer;

	public BoardSquare(SquareType type) {
		typeOfSquare = type;
		colorOfPlayer = PlayerColor.None;
	}
	
	public BoardSquare(SquareType type, PlayerColor color) {
		typeOfSquare = type;
		colorOfPlayer = color;
	}
	
	public SquareType getSquareType() {
		return typeOfSquare;
	}
	
	public PlayerColor getColor() {
		return colorOfPlayer;
	}
}
