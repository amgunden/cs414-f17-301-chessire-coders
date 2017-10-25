package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class RatPiece extends GamePiece {

	public RatPiece(int row, int column, PlayerColor color) {
		super(row, column, color);
		setPowerDefault();
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(1);
	}
	
	@Override
	public boolean canOccupy(BoardSquare square) {
		if (square == null)
			return false;
		
		boolean result = true;
		
		if (!square.isEmpty())
		{
			if ( (square.getPiece().getPowerLevel() != 8) && (square.getPiece().getPowerLevel() > this.getPowerLevel()) ) {
				result = false;
			}
			if (square.getPiece().getColor() == this.getColor()) {
				result = false;
			}
		}
		
		result = result && squareIsAdjacent(square);
		result = result && !isFriendlyDen(square);
		
		return result;
	}

}
