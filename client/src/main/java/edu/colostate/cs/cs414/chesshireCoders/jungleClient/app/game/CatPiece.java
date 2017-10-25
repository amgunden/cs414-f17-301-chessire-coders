package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class CatPiece extends GamePiece {

	public CatPiece(int row, int column, PlayerColor color) {
		super(row, column, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(2);
	}

}
