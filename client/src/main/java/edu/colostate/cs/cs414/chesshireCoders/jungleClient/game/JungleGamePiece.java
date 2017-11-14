package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public abstract class JungleGamePiece extends GamePiece{
	private int powerLevel = 0;
	
	 public JungleGamePiece(PlayerEnumType ownerType, int column, int row) {
	        super(ownerType, null, column, row);
	 }
	
	public JungleGamePiece(JungleGamePiece piece) {
		setRow(piece.getRow());
		setColumn(piece.getColumn());
		setPlayerOwner(piece.getPlayerOwner());
	}
  
	public void setLocation(int row, int column) {
		if ( (column < 0) || (column > 6) ) {
			throw new RuntimeException("Column index out of bounds: " + column);
		}
		if ( (row < 0) || (row > 8) ) {
			throw new RuntimeException("Row index out of bounds: " + row);
		}
		
		setRow(row);
		setColumn(column);
	}
	
	public int getPowerLevel() {
		return powerLevel;
	}
	
	public void setPowerLevel(int power) {
		powerLevel = Math.min(power, 8);
		powerLevel = Math.max(powerLevel, 0);
	}
	
	public abstract void setPowerDefault();

	public boolean canOccupy(BoardSquare square) {
		if (square == null)
			return false;
		
		boolean result = true;
		
		result = result && canCapture(square);
		result = result && squareIsAdjacent(square);
		result = result && !(square instanceof RiverSquare);
		result = result && !isFriendlyDen(square);
		
		return result;
	}

	protected boolean isFriendlyDen(BoardSquare square) {
		return square instanceof DenSquare && square.getPlayerOwner()==this.getPlayerOwner();
	}

	protected boolean squareIsAdjacent(BoardSquare square) {
		return Math.abs(square.getColumn() - getColumn()) <= 1 && Math.abs(square.getRow() - getRow()) <= 1;
	}

	protected boolean canCapture(BoardSquare square) {
		if (!square.isEmpty())
		{
			if (square.getPiece().getPowerLevel() > this.getPowerLevel()) {
				return false;
			}
			if (square.getPiece().getPlayerOwner() == this.getPlayerOwner()) {
				return false;
			}
		}
		
		return true;
	}
	
}
