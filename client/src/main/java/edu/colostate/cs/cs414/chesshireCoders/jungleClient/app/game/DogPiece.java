package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class DogPiece extends GamePiece {

	public DogPiece(int row, int column, PlayerColor color) {
		super(row, column, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(4);
	}
	
}
