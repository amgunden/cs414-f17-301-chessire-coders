package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class TigerPiece extends GamePiece {

	public TigerPiece(int column, int row, PlayerColor color) {
		super(column, row, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(6);
	}
	
}
