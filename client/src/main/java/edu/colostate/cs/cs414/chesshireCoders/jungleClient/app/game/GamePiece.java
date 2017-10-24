package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;


public abstract class GamePiece {
	private int powerLevel = 0;
	int row = 0;
	int column = 0;
	PlayerColor color;
	
	public GamePiece(int column, int row, PlayerColor color) {
		this.row = row;
		this.column = column;
		this.color = color;
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
		powerLevel = Math.max(power, 1);
		powerLevel = Math.min(power, 8);
	}
	
	public void setTrapped(boolean val)
	{
		if (val)
			setPowerLevel(1);
		else
			setPowerDefault();
	}
	
	public abstract void setPowerDefault();

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
