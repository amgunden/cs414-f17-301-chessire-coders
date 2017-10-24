package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;


public class BoardSquare {
	SquareType typeOfSquare = SquareType.Normal;
	PlayerColor colorOfPlayer;
	protected GamePiece piece;

	public GamePiece getPiece() {
		return piece;
	}

	public void clearPiece() {
		this.piece = new RatPiece(0, 0, colorOfPlayer);
		piece = null;
	}
	
	public void setPiece(GamePiece piece) {
		piece.setPowerDefault();
		this.piece = piece;
	}

	public BoardSquare(GamePiece piece) {
		setPiece(piece);
		colorOfPlayer = PlayerColor.None;
	}
	
	public BoardSquare(GamePiece piece, PlayerColor color) {
		setPiece(piece);
		colorOfPlayer = color;
	}
	
	public SquareType getSquareType() {
		return typeOfSquare;
	}
	
	public PlayerColor getColor() {
		return colorOfPlayer;
	}
	
	public boolean isEmpty()
	{
		return (piece == null);
	}
}
