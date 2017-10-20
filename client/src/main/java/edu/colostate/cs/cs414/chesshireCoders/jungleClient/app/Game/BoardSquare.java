package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

public class BoardSquare {
	SquareType typeOfSquare = SquareType.Normal;
	//GamePiece occupyingPiece = new GamePiece(PieceType.Rat, 0, 0);

	public SquareType getSquareType() {
		return typeOfSquare;
	}
	
	//Possible methods
	/*
	public boolean setPiece(GamePiece piece) {
		return true;
	}
	
	public GamePiece getPiece() {
		return occupyingPiece;
	}
	
	public boolean canOccupy(GamePiece comparisonPiece) {
		return true;
	}
	*/
}
