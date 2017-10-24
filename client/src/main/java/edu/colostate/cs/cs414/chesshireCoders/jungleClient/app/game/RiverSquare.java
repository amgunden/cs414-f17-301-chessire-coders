package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class RiverSquare extends BoardSquare {

	public RiverSquare(GamePiece piece) {
		super(piece);
		// TODO Auto-generated constructor stub
	}

	public RiverSquare(GamePiece piece, PlayerColor color) {
		super(piece, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPiece(GamePiece piece) {
		piece.setPowerLevel(0);
		this.piece = piece;
	}
}
