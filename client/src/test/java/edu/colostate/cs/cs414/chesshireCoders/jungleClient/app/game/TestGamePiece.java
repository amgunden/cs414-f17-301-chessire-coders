package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestGamePiece {
	GamePiece piece = new DogPiece(0, 0, PlayerColor.Red);
	
	@Test
	public void testSetLocation_bottom_right_corner() {
		piece.setLocation(8, 6);
		
		assertEquals("The piece should be in row 8", 8, piece.getRow());
		assertEquals("The piece should be in column 6", 6, piece.getColumn());
		piece.setLocation(0, 0);
	}
	
	@Test
	public void testSetLocation_column_too_big() {
		try {
			piece.setLocation(0, -1);
			fail("An exception should have been thrown due to the invalid index.");
		}catch (Exception e) {
			piece.setLocation(0, 0);
		}
	}
	
	@Test
	public void testSetLocation_column_too_small() {
		try {
			piece.setLocation(0, -1);
			fail("An exception should have been thrown due to the invalid index.");
		}catch (Exception e) {
			piece.setLocation(0, 0);
		}
	}
	
	@Test
	public void testSetLocation_row_too_big() {
		try {
			piece.setLocation(9, 0);
			fail("An exception should have been thrown due to the invalid index.");
		}catch (Exception e) {
			piece.setLocation(0, 0);
		}
	}
	
	@Test
	public void testSetLocation_row_too_small() {
		try {
			piece.setLocation(-1, 0);
			fail("An exception should have been thrown due to the invalid index.");
		}catch (Exception e) {
			piece.setLocation(0, 0);
		}
	}

	@Test
	public void testSetPowerLevel_zero() {
		piece.setPowerLevel(0);
		
		assertEquals("The piece should have power level 0", 0, piece.getPowerLevel());
	}
	
	@Test
	public void testSetPowerLevel_too_small() {
		piece.setPowerLevel(-1);
		
		assertEquals("The piece should have power level 0", 0, piece.getPowerLevel());
	}
	
	@Test
	public void testSetPowerLevel_eight() {
		piece.setPowerLevel(8);

		assertEquals("The piece should have power level 8", 8, piece.getPowerLevel());
	}
	
	@Test
	public void testSetPowerLevel_too_big() {
		piece.setPowerLevel(9);

		assertEquals("The piece should have power level 8", 8, piece.getPowerLevel());
	}

	@Test
	public void testCanOccupy_river() {
		piece.setLocation(0, 0);
		BoardSquare river = new RiverSquare(1, 0, null);
		assertFalse("A normal piece should not be able to enter a river square.", piece.canOccupy(river));
	}
	
	@Test
	public void testCanOccupy_river_rat() {
		piece = new RatPiece(0, 0, PlayerColor.Red);
		BoardSquare river = new RiverSquare(1, 0, null);
		assertTrue("A rat should be able to enter a river square.", piece.canOccupy(river));
	}
	
	@Test
	public void testCanOccupy_friendly_piece() {
		piece.setLocation(0, 0);
		BoardSquare square = new BoardSquare(1, 0, new CatPiece(1, 0, PlayerColor.Red));
		assertFalse("A normal piece should not be able to enter a square occupied by a friendly piece.", piece.canOccupy(square));
	}
	
	@Test
	public void testCanOccupy_friendly_den() {
		piece.setLocation(0, 0);
		BoardSquare square = new DenSquare(1, 0, null, PlayerColor.Red);
		assertFalse("A normal piece should not be able to enter a friendly den square.", piece.canOccupy(square));
	}
	
	@Test
	public void testCanOccupy_square_not_adjacent_row() {
		piece.setLocation(0, 0);
		BoardSquare square = new BoardSquare(2, 0, null);
		assertFalse("A normal piece should not be able to enter a square that is not right next to it.", piece.canOccupy(square));
	}
	
	@Test
	public void testCanOccupy_square_not_adjacent_column() {
		piece.setLocation(0, 0);
		BoardSquare square = new BoardSquare(0, 2, null);
		assertFalse("A normal piece should not be able to enter a square that is not right next to it.", piece.canOccupy(square));
	}
	
	@Test
	public void testCanOccupy_square_not_adjacent_leopard() {
		piece = new LeopardPiece(0, 0, PlayerColor.Red);
		piece.setLocation(0, 0);
		BoardSquare square = new BoardSquare(0, 2, null);
		assertTrue("A leopard piece should be able to enter a square that is not right next to it.", piece.canOccupy(square));
	}

}
