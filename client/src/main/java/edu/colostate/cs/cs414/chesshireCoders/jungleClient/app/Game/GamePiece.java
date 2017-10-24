package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;


public class GamePiece {
	PieceType typeOfPiece = PieceType.Rat;
	int row = 0;
	int column = 0;
	PlayerColor color;
	
	public GamePiece(PieceType typeOfPiece, int column, int row, PlayerColor color) {
		this.typeOfPiece = typeOfPiece;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public void changeLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	//Getter/Setters
	public PlayerColor getColor() {
		return color;
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
