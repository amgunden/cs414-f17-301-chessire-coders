package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class FoxPiece extends GamePiece {

	public FoxPiece(int row, int column, PlayerColor color) {
		super(row, column, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(3);
	}

}
