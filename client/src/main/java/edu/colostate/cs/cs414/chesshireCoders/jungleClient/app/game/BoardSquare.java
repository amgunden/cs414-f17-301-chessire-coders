package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class BoardSquare {
	private int[] location;
	PlayerEnumType playerOwner;
	protected JungleGamePiece piece;

	public BoardSquare(int row, int col, JungleGamePiece piece) {
		location = new int[]{row, col};
		if (piece != null)
			setPiece(piece);
		playerOwner = null;
	}

	public BoardSquare(int row, int col, JungleGamePiece piece, PlayerEnumType owner) {
		location = new int[]{row, col};
		if (piece != null)
			setPiece(piece);
		playerOwner = owner;
	}
	
  // Remove a piece from the square.
	public void clearPiece() {
		this.piece = new RatPiece(playerOwner, 0, 0);
		piece = null;
	}

	public PlayerEnumType getPlayerOwner() {
        return playerOwner;
    }
	
	public int getColumn() {
		return location[1];
	}
	
	public JungleGamePiece getPiece() {
		return piece;
	}
	
	public int getRow() {
		return location[0];
	}
	
	public boolean isEmpty()
	{
		return (piece == null);
	}
	
	public void setPiece(JungleGamePiece piece) {
		if (piece != null) {
			piece.setRow(this.getRow());
			piece.setColumn(this.getColumn());
			piece.setPowerDefault();
		}
		
		this.piece = piece;
	}
}
