package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class LionPiece extends GamePiece {

	public LionPiece(int column, int row, PlayerColor color) {
		super(column, row, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(7);
	}
	
}
