package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class ElephantPiece extends JungleGamePiece {

	public ElephantPiece(PlayerEnumType ownerType, int column, int row) {
        super(ownerType,  column, row);
		setPowerDefault();
		setPieceType(PieceType.ELEPHANT);
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(8);
	}

}
