package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public abstract class GamePiece {
	private int powerLevel = 0;
	int row = 0;
	int column = 0;
	PlayerColor color;
	
	public GamePiece(int row, int column, PlayerColor color) {
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public GamePiece(GamePiece piece) {
		this.row = piece.getRow();
		this.column = piece.getColumn();
		this.color = piece.getColor();
	}
  
	public void changeLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	//Getter/Setters
	public PlayerColor getColor() {
		return color;
	}
	
	public int getPowerLevel() {
		return powerLevel;
	}
	
	public void setPowerLevel(int power) {
		powerLevel = Math.max(power, 0);
		powerLevel = Math.min(power, 8);
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
		return square instanceof DenSquare && square.getColor()==this.getColor();
	}

	protected boolean squareIsAdjacent(BoardSquare square) {
		return Math.abs(square.getColumn() - this.column) <= 1 && Math.abs(square.getRow() - this.row) <= 1;
	}

	protected boolean canCapture(BoardSquare square) {
		if (!square.isEmpty())
		{
			if (square.getPiece().getPowerLevel() > this.getPowerLevel()) {
				return false;
			}
			if (square.getPiece().getColor() == this.getColor()) {
				return false;
			}
		}
		
		return true;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
}
