package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class TigerPiece extends GamePiece {

	public TigerPiece(int row, int column, PlayerColor color) {
		super(row, column, color);
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
		setPowerLevel(6);
	}
	
}
