package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class LeopardPiece extends GamePiece {

	public LeopardPiece(int column, int row, PlayerColor color) {
		super(column, row, color);
		setPowerDefault();
	}


	@Override
	public boolean canOccupy(BoardSquare square) {
		if (square == null)
			return false;
		
		boolean result = true;
		
		result = result && canCapture(square);
		result = result && !(square instanceof RiverSquare);
		result = result && !isFriendlyDen(square);
		
		return result;
	}
	
	@Override
	public void setPowerDefault() {
		setPowerLevel(5);
	}

}
