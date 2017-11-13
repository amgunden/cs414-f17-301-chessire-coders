package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class CatPiece extends JungleGamePiece {

	public CatPiece(PlayerEnumType ownerType, int column, int row) {
        super(ownerType,  column, row);
		setPowerDefault();
		setPieceType(PieceType.CAT);
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(2);
	}

}
