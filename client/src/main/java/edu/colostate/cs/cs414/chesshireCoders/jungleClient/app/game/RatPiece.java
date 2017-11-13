package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class RatPiece extends JungleGamePiece {

	public RatPiece(PlayerEnumType ownerType, int column, int row) {
        super(ownerType,  column, row);
		setPowerDefault();
		setPieceType(PieceType.RAT);
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
			if (square.getPiece().getPlayerOwner() == this.getPlayerOwner()) {
				result = false;
			}
		}
		
		result = result && squareIsAdjacent(square);
		result = result && !isFriendlyDen(square);
		
		return result;
	}

}
