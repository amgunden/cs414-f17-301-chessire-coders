package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class TrapSquare extends BoardSquare {

	public TrapSquare(GamePiece piece) {
		super(piece);
		// TODO Auto-generated constructor stub
	}

	public TrapSquare(GamePiece piece, PlayerColor color) {
		super(piece, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPiece(GamePiece piece) {
		piece.setPowerLevel(1);
		this.piece = piece;
	}
}
