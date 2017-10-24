package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class DogPiece extends GamePiece {

	public DogPiece(int column, int row, PlayerColor color) {
		super(column, row, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(4);
	}
	
}
