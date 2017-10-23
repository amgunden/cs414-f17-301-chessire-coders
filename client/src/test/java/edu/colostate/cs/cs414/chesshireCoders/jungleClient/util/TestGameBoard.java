package edu.colostate.cs.cs414.chesshireCoders.jungleClient.util;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

public class TestGameBoard {
	GameBoard testBoard;
	
	@Before
	public void init() {
		testBoard = new GameBoard();
	}
	
	//Currently in development. Check back later.
	@Test
	public void testCheckMove() {
		//test you can move by one
		assertTrue(testBoard.checkMove(0, 0, 0, 1));
		//test you cannot move by 2
		assertFalse(testBoard.checkMove(0, 1, 0, 2));
	}
}
