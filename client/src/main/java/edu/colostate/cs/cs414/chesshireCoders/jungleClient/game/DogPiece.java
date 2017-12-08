package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class DogPiece extends JungleGamePiece {

	public DogPiece() {
	}

	public DogPiece(PlayerEnumType ownerType, int column, int row) {
        super(ownerType, column, row);
		setPowerDefault();
		setPieceType(PieceType.DOG);
	}

	@Override
	public void setPowerDefault() {
		setPowerLevel(4);
	}
	
}
