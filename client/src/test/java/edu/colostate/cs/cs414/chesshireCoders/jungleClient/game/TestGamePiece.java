package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import org.junit.Test;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.BoardSquare;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.CatPiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.DenSquare;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.DogPiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.LeopardPiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.RatPiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.RiverSquare;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import static org.junit.Assert.*;

public class TestGamePiece {
	JungleGamePiece piece = new DogPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
	
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
		piece = new RatPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
		BoardSquare river = new RiverSquare(1, 0, null);
		assertTrue("A rat should be able to enter a river square.", piece.canOccupy(river));
	}
	
	@Test
	public void testCanOccupy_friendly_piece() {
		piece.setLocation(0, 0);
		BoardSquare square = new BoardSquare(1, 0, new CatPiece(PlayerEnumType.PLAYER_ONE, 1, 0));
		assertFalse("A normal piece should not be able to enter a square occupied by a friendly piece.", piece.canOccupy(square));
	}
	
	@Test
	public void testCanOccupy_friendly_den() {
		piece.setLocation(0, 0);
		BoardSquare square = new DenSquare(1, 0, null, PlayerEnumType.PLAYER_ONE);
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
		piece = new LeopardPiece(PlayerEnumType.PLAYER_ONE, 0, 0);
		piece.setLocation(0, 0);
		BoardSquare square = new BoardSquare(0, 2, null);
		assertTrue("A leopard piece should be able to enter a square that is not right next to it.", piece.canOccupy(square));
	}

}
