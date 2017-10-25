package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBoardSquare {

	@Test
	public void testConstructorWithPlayerColor() {
		BoardSquare square = new BoardSquare(0, 0, null, PlayerColor.Red);
		
		assertEquals("The square should have a color of Red.", PlayerColor.Red, square.getColor());
	}

	@Test
	public void testClearPiece() {
		GamePiece piece = new RatPiece(0, 0, PlayerColor.Red);
		BoardSquare square = new BoardSquare(0, 0, piece);
		
		square.clearPiece();
		
		assertNull("The square should not have a piece after clearPiece is called.", square.getPiece());
	}

	@Test
	public void testIsEmpty() {
		BoardSquare square = new BoardSquare(0, 0, null);
		
		assertTrue("The square should be empty.", square.isEmpty());
	}
	
	@Test
	public void testIsEmpty_not() {
		BoardSquare square = new BoardSquare(0, 0, new RatPiece(0, 0, PlayerColor.Red));
		
		assertFalse("The square should not be empty.", square.isEmpty());
	}

	@Test
	public void testSetPiece() {
		GamePiece piece = new RatPiece(0, 0, PlayerColor.Red);
		BoardSquare square = new BoardSquare(0, 0, null);
		
		square.setPiece(piece);
		
		assertEquals("The square should contain the piece that was set in it.", piece, square.getPiece());
	}
	
	@Test
	public void testSetPiece_null() {
		GamePiece piece = new RatPiece(0, 0, PlayerColor.Red);
		BoardSquare square = new BoardSquare(0, 0, piece);
		
		square.setPiece(null);
		
		assertNull("The square should be empty.", square.getPiece());
	}

}
