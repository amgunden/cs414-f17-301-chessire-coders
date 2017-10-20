package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

public class GamePiece {
	PieceType typeOfPiece = PieceType.Rat;
	int row = 0;
	int column = 0;
	
	public GamePiece(PieceType typeOfPiece, int row, int column) {
		this.typeOfPiece = typeOfPiece;
		this.row = row;
		this.column = column;
	}
	
	public PieceType getPieceType() {
		return typeOfPiece;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
