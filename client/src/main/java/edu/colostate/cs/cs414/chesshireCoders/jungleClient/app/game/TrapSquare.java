package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class TrapSquare extends BoardSquare {

	public TrapSquare(int col, int row, GamePiece piece) {
		super(col, row, piece);
		// TODO Auto-generated constructor stub
	}

	public TrapSquare(int col, int row, GamePiece piece, PlayerColor color) {
		super(col, row, piece);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPiece(GamePiece piece) {
		piece.setRow(this.getRow());
		piece.setColumn(this.getColumn());
		piece.setPowerLevel(1);
		this.piece = piece;
	}
}
