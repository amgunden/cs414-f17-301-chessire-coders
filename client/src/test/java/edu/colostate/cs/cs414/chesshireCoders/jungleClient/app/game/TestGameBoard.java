package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestGameBoard {
	GameBoard board = new GameBoard();

	@Test
	public void testGetPieceAt_normal() {
		GamePiece piece = new RatPiece(0,0,PlayerColor.Black);
		board.getSquareAt(0, 0).setPiece(piece);
		
		GamePiece result = board.getPieceAt(0, 0);
		
		assertNotNull("The piece returned from the square (0,0) should not be null.", result);
		assertEquals("The piece returned from the square (0,0) should equal the piece placed there.", piece, result);
	}
	
	@Test
	public void testGetPieceAt_column_too_big() {
		GamePiece piece = board.getPieceAt(0, 7);

		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_column_too_small() {
		GamePiece piece = board.getPieceAt(0, -1);

		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_empty_square() {
		GamePiece piece = board.getPieceAt(1, 0);
		
		assertNull("The piece returned from the empty square should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_row_too_big() {
		GamePiece piece = board.getPieceAt(9, 0);

		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_row_too_small() {
		GamePiece piece = board.getPieceAt(-1, 0);
		
		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetSquareAt_top_left_table_edge() {
		BoardSquare result = board.getSquareAt(0, 0);
		
		assertNotNull("The square returned from the location (0,0) should not be null.", result);
	}
	
	@Test
	public void testGetSquareAt_bottom_right_table_edge() {
		BoardSquare result = board.getSquareAt(8, 6);
		
		assertNotNull("The square returned from the location (6,8) should not be null.", result);
	}
	
	@Test
	public void testGetSquareAt_column_too_big() {
		BoardSquare square = board.getSquareAt(0, 7);

		assertNull("The square returned from the invalid bounds should be null.", square);
	}
	
	@Test
	public void testGetSquareAt_column_too_small() {
		BoardSquare square = board.getSquareAt(0, -1);

		assertNull("The square returned from the invalid bounds should be null.", square);
	}
	
	@Test
	public void testGetSquareAt_row_too_big() {
		BoardSquare square = board.getSquareAt(9, 0);

		assertNull("The square returned from the invalid bounds should be null.", square);
	}
	
	@Test
	public void testGetSquareAt_row_too_small() {
		BoardSquare square = board.getSquareAt(-1, 0);

		assertNull("The square returned from the invalid bounds should be null.", square);
	}

	@Test
	public void testGetValidMoves() {
		int[] expected = {0,0,1,1};
		int[] result = board.getValidMoves(0, 0);
		
		assertArrayEquals(expected, result);
	}

	@Test
	public void testMovePiece() {
		int[] from = {0,0};
		int[] to = {0,1};
		
		board.movePiece(from, to);
		
		GamePiece result = board.getPieceAt(0, 0);
		assertNull("The piece returned from the square (0,0) should be null.", result);
		
		result = board.getPieceAt(0, 1);
		assertNotNull("The piece returned from the square (0,1) should not be null.", result);
		board = new GameBoard();
	}

}
