package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import org.junit.Test;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.BoardSquare;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.GameBoard;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.RatPiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerColor;

import static org.junit.Assert.*;

public class TestGameBoard {
	GameBoard board = new GameBoard();

	@Test
	public void testGetPieceAt_normal() {
		JungleGamePiece piece = new RatPiece(PlayerEnumType.PLAYER_TWO, 0, 0);
		board.getSquareAt(0, 0).setPiece(piece);
		
		JungleGamePiece result = board.getPieceAt(0, 0);
		
		assertNotNull("The piece returned from the square (0,0) should not be null.", result);
		assertEquals("The piece returned from the square (0,0) should equal the piece placed there.", piece, result);
	}
	
	@Test
	public void testGetPieceAt_column_too_big() {
		JungleGamePiece piece = board.getPieceAt(0, 7);

		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_column_too_small() {
		JungleGamePiece piece = board.getPieceAt(0, -1);

		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_empty_square() {
		JungleGamePiece piece = board.getPieceAt(1, 0);
		
		assertNull("The piece returned from the empty square should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_row_too_big() {
		JungleGamePiece piece = board.getPieceAt(9, 0);

		assertNull("The piece returned from the invalid bounds should be null.", piece);
	}
	
	@Test
	public void testGetPieceAt_row_too_small() {
		JungleGamePiece piece = board.getPieceAt(-1, 0);
		
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
	public void testGetValidMoves_corner() {
		int[] expected = {0,0,1,1};
		int[] result = board.getValidMoves(0, 0);
		
		assertArrayEquals(expected, result);
	}
	
	@Test
	public void testGetValidMoves_leopard() {
		int[] expected = {-1,-1,1,4};
		int[] result = board.getValidMoves(2, 2);
		
		assertArrayEquals(expected, result);
	}

	@Test
	public void testMovePiece() {
		int[] from = {0,0};
		int[] to = {0,1};
		
		board.movePiece(from, to);
		
		JungleGamePiece result = board.getPieceAt(0, 0);
		assertNull("The piece returned from the square (0,0) should be null.", result);
		
		result = board.getPieceAt(0, 1);
		assertNotNull("The piece returned from the square (0,1) should not be null.", result);
		board = new GameBoard();
	}
	
	@Test
	public void testMovePieceOneSpace() {
		int[] from = {1,1};
		int[] to = {1,0};
		assertEquals(PieceType.DOG,board.getPieceAt(1, 1).getPieceType());
		
		board.movePiece(from, to);
		assertEquals(PieceType.DOG,board.getPieceAt(1, 0).getPieceType());
		
	}
	
	@Test
	public void testMovePieceIntoOwnDen() {
		JungleGamePiece piece1 = new RatPiece(PlayerEnumType.PLAYER_TWO, 2, 0);
		board.getSquareAt(0, 2).setPiece(piece1);
		assertEquals(PieceType.RAT, board.getPieceAt(0, 2).getPieceType());
		
		int[] expected = {-1,0,0,1};
		int[] valid = board.getValidMoves(0, 2);
		assertArrayEquals(expected, valid);
	}
	
	@Test
	public void testMovePieceIntoOpponentsDen() {
		JungleGamePiece piece1 = new RatPiece(PlayerEnumType.PLAYER_ONE, 2, 0);
		board.getSquareAt(0, 2).setPiece(piece1);
		assertEquals(PieceType.RAT, board.getPieceAt(0, 2).getPieceType());
		
		int[] expected = {-1,0,1,1};
		int[] valid = board.getValidMoves(0, 2);
		assertArrayEquals(expected, valid);
	}
	
	@Test
	public void testGameIsWon() {
		JungleGamePiece piece1 = new RatPiece(PlayerEnumType.PLAYER_ONE, 3, 0);
		board.getSquareAt(0, 3).setPiece(piece1);
		
		assertTrue(board.isGameOver());
	}
	
	@Test
	public void testPlayerOneWon() {
		JungleGamePiece piece1 = new RatPiece(PlayerEnumType.PLAYER_ONE, 3, 0);
		board.getSquareAt(0, 3).setPiece(piece1);
		
		assertEquals(PlayerColor.RED,board.getWinner());
	}

}
