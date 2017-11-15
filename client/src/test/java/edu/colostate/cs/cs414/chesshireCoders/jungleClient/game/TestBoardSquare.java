package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import org.junit.Test;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.BoardSquare;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.RatPiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import static org.junit.Assert.*;

public class TestBoardSquare {

	@Test
	public void testConstructorWithPlayerColor() {
		BoardSquare square = new BoardSquare(0, 0, null, PlayerEnumType.PLAYER_ONE);
		
		assertEquals("The square should belong to player 1.", PlayerEnumType.PLAYER_ONE, square.getPlayerOwner());
	}

	@Test
	public void testClearPiece() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
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
		BoardSquare square = new BoardSquare(0, 0, new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0));
		
		assertFalse("The square should not be empty.", square.isEmpty());
	}

	@Test
	public void testSetPiece() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
		BoardSquare square = new BoardSquare(0, 0, null);
		
		square.setPiece(piece);
		
		assertEquals("The square should contain the piece that was set in it.", piece, square.getPiece());
	}
	
	@Test
	public void testSetPiece_null() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
		BoardSquare square = new BoardSquare(0, 0, piece);
		
		square.setPiece(null);
		
		assertNull("The square should be empty.", square.getPiece());
	}
	
	@Test
	public void testGetPiece() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
		BoardSquare square = new BoardSquare(0, 0, piece, PlayerEnumType.PLAYER_ONE);
		square.setPiece(piece);
		
		assertEquals(piece, square.getPiece());	
	}
	
	@Test
	public void testGetPlayerOwner() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
		BoardSquare square = new BoardSquare(0, 0, piece, PlayerEnumType.PLAYER_ONE);
		square.setPiece(piece);
		
		assertEquals(PlayerEnumType.PLAYER_ONE, square.getPlayerOwner());	
	}
	
	@Test
	public void testGetPlayerOwner_null() {
		JungleGamePiece piece = new RatPiece(null, 0, 0);
		BoardSquare square = new BoardSquare(0, 0, piece);
		square.setPiece(piece);
		
		assertEquals(null, square.getPlayerOwner());	
	}
	
	@Test
	public void testGetRow() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 5, 0);
		BoardSquare square = new BoardSquare(5, 0, piece, PlayerEnumType.PLAYER_ONE);
		square.setPiece(piece);
		
		assertEquals(5, square.getRow());
	}
	
	@Test
	public void testGetRow_null() {
		BoardSquare square = new BoardSquare(5, 0, null);
		
		assertEquals(5, square.getRow());
	}
	
	@Test
	public void testGetColumn() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 5);
		BoardSquare square = new BoardSquare(0, 0, piece, PlayerEnumType.PLAYER_ONE);
		square.setPiece(piece);
		
		assertEquals(0, square.getColumn());
	}
	
	@Test
	public void testGetColumn_null() {
		BoardSquare square = new BoardSquare(0, 5, null);
		
		assertEquals(5, square.getColumn());
	}

}
