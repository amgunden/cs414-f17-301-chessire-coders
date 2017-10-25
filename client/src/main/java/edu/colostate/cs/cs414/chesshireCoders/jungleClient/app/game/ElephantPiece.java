package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class ElephantPiece extends GamePiece {

	public ElephantPiece(int row, int column, PlayerColor color) {
		super(row, column, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(8);
	}

}
