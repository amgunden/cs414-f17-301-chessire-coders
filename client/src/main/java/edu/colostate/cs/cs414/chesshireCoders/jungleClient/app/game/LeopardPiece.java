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
		
		if (!square.isEmpty())
		{
			if (square.getPiece().getPowerLevel() > this.getPowerLevel()) {
				return false;
			}
			if (square.getPiece().getColor() == this.getColor()) {
				return false;
			}
		}
		
		if (square instanceof RiverSquare) {
			return false;
		}
		
		if (square instanceof DenSquare && square.getColor()==this.getColor()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void setPowerDefault() {
		setPowerLevel(5);
	}

}
