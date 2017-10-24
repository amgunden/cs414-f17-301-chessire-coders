package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class RatPiece extends GamePiece {

	public RatPiece(int column, int row, PlayerColor color) {
		super(column, row, color);
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
		
		if (!square.isEmpty())
		{
			if ( (square.getPiece().getPowerLevel() != 8) && (square.getPiece().getPowerLevel() > this.getPowerLevel()) ) {
				return false;
			}
			if (square.getPiece().getColor() == this.getColor()) {
				return false;
			}
		}
		
		return true;
	}

}
