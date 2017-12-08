package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GameBoard implements Cloneable {

	//Set up Squares
	BoardSquare[][] boardSquares = null;

	public GameBoard() {
		setUpBoard();
	}
	
	public GameBoard(List<GamePiece> pieces) {
		setUpBoard(pieces);
	}

	public void clearBoard() {
		for (int row = 0; row < boardSquares.length; row++) {
			for (int col = 0; col < boardSquares[0].length; col++) {
				removePieceAt(row, col);
			}
		}
	}

	public JungleGamePiece getPieceAt(int row, int col) {
		if ( (col < 0) || (col > 6) || (row < 0) || (row > 8) )
			return null;
		
		return getSquareAt(row, col).getPiece();
	}
	
	public List<GamePiece> getPieces()
	{
		List<GamePiece> pieces = new ArrayList<>();
		
		for (int row = 0; row < boardSquares.length; row++) {
			for (int col = 0; col < boardSquares[0].length; col++) {
				JungleGamePiece p = getPieceAt(row, col);
				if (p != null)
					pieces.add(p);
			}
		}
		
		return pieces;
	}
	
	public BoardSquare getSquareAt(int row, int col) {
		if ( (col < 0) || (col > 6) || (row < 0) || (row > 8) )
			return null;
		
		return boardSquares[row][col];
	}
	
	/**
	 * Gets the valid moves for the piece located at (row, column) in the array.
	 * @param row the row location of the piece on the board.
	 * @param column the column location of the piece on the  board.
	 * @return an array of integers specifying the distance of each valid move in each direction [L,U,R,D].
	 */
	public int[] getValidMoves(int row, int column) {
		int[] validMoves = new int[4];
		
		JungleGamePiece piece = getPieceAt(row, column);

		if (piece != null) {
			validMoves[0] = getValidMoveHorizontal(piece, -1); // left
			validMoves[1] = getValidMoveVertical(piece, -1); // up
			validMoves[2] = getValidMoveHorizontal(piece, 1); // right
			validMoves[3] = getValidMoveVertical(piece, 1); // down

			return validMoves;
		} else return null;
	}

	
	
	public void movePiece(int[] from, int[] to) {
		if (from == null || to == null) throw new IllegalArgumentException("Input cannot be null.");

		BoardSquare fromSquare = getSquareAt(from[0],from[1]);
		BoardSquare toSquare = getSquareAt(to[0],to[1]);
		
		toSquare.setPiece( fromSquare.clearPiece() );
	}
	
	public void placePieceAt(int row, int column, JungleGamePiece piece)
	{
		BoardSquare square = getSquareAt(row, column);
		piece.setLocation(row, column);
		square.setPiece(piece);
	}

	public GamePiece removePieceAt(int row, int column)
	{
		BoardSquare square = getSquareAt(row, column);
		return square.clearPiece();
	}
	
	/**
	 * Sets up a board with the default piece configuration.
	 */
	public void setUpBoard() {
		List<GamePiece> pieces = new ArrayList<>();
		// Player TWO
		pieces.add(new LionPiece(PlayerEnumType.PLAYER_TWO, 0, 0));
		pieces.add(new TigerPiece(PlayerEnumType.PLAYER_TWO, 6, 0));
		pieces.add(new DogPiece(PlayerEnumType.PLAYER_TWO, 1, 1));
		pieces.add(new CatPiece(PlayerEnumType.PLAYER_TWO, 5, 1));
		pieces.add(new RatPiece(PlayerEnumType.PLAYER_TWO, 0, 2));
		pieces.add(new LeopardPiece(PlayerEnumType.PLAYER_TWO, 2, 2));
		pieces.add(new FoxPiece(PlayerEnumType.PLAYER_TWO, 4, 2));
		pieces.add(new ElephantPiece(PlayerEnumType.PLAYER_TWO, 6, 2));
		// Player ONE
		pieces.add(new ElephantPiece(PlayerEnumType.PLAYER_ONE, 0, 6));
		pieces.add(new FoxPiece(PlayerEnumType.PLAYER_ONE, 2, 6));
		pieces.add(new LeopardPiece(PlayerEnumType.PLAYER_ONE, 4, 6));
		pieces.add(new RatPiece(PlayerEnumType.PLAYER_ONE, 6, 6));
		pieces.add(new CatPiece(PlayerEnumType.PLAYER_ONE, 1, 7));
		pieces.add(new DogPiece(PlayerEnumType.PLAYER_ONE, 5, 7));
		pieces.add(new TigerPiece(PlayerEnumType.PLAYER_ONE, 0, 8));
		pieces.add(new LionPiece(PlayerEnumType.PLAYER_ONE, 6, 8));
		
		setUpBoard(pieces);
	}
	
	/**
	 * Sets up a board and places the pieces on the board.
	 * @param pieces
	 */
	public void setUpBoard(List<GamePiece> pieces) {
		if (pieces == null || pieces.isEmpty()) {
			setUpBoard();
			return;
		}
		
		if (boardSquares == null) {
			createBoard();
		} else {
			clearBoard();
		}
		
		
		for (Iterator<GamePiece> iterator = pieces.iterator(); iterator.hasNext();) {
			JungleGamePiece piece = JungleGamePieceFactory.castPieceDown(iterator.next());
			
			placePieceAt(piece.getRow(), piece.getColumn(), piece);
		}
	}
	
	private void createBoard() {	
		boardSquares = new BoardSquare[9][7];
		//row 1
		boardSquares[0][0] = new BoardSquare(0, 0, null);
		boardSquares[0][1] = new BoardSquare(0, 1, null);
		boardSquares[0][2] = new TrapSquare(0, 2, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[0][3] = new DenSquare(0, 3, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[0][4] = new TrapSquare(0, 4, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[0][5] = new BoardSquare(0, 5, null);
		boardSquares[0][6] = new BoardSquare(0, 6, null);
		//row 2
		boardSquares[1][0] = new BoardSquare(1, 0, null);
		boardSquares[1][1] = new BoardSquare(1, 1, null);
		boardSquares[1][2] = new BoardSquare(1, 2, null);
		boardSquares[1][3] = new TrapSquare(1, 3, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[1][4] = new BoardSquare(1, 4, null);
		boardSquares[1][5] = new BoardSquare(1, 5, null);
		boardSquares[1][6] = new BoardSquare(1, 6, null);
		//row 3
		boardSquares[2][0] = new BoardSquare(2, 0, null);
		boardSquares[2][1] = new BoardSquare(2, 1, null);
		boardSquares[2][2] = new BoardSquare(2, 2, null);
		boardSquares[2][3] = new BoardSquare(2, 3, null);
		boardSquares[2][4] = new BoardSquare(2, 4, null);
		boardSquares[2][5] = new BoardSquare(2, 5, null);
		boardSquares[2][6] = new BoardSquare(2, 6, null);
		//row 4
		boardSquares[3][0] = new BoardSquare(3, 0, null);
		boardSquares[3][1] = new RiverSquare(3, 1, null);
		boardSquares[3][2] = new RiverSquare(3, 2, null);
		boardSquares[3][3] = new BoardSquare(3, 3, null);
		boardSquares[3][4] = new RiverSquare(3, 4, null);
		boardSquares[3][5] = new RiverSquare(3, 5, null);
		boardSquares[3][6] = new BoardSquare(3, 6, null);
		//row 5
		boardSquares[4][0] = new BoardSquare(4, 0, null);
		boardSquares[4][1] = new RiverSquare(4, 1, null);
		boardSquares[4][2] = new RiverSquare(4, 2, null);
		boardSquares[4][3] = new BoardSquare(4, 3, null);
		boardSquares[4][4] = new RiverSquare(4, 4, null);
		boardSquares[4][5] = new RiverSquare(4, 5, null);
		boardSquares[4][6] = new BoardSquare(4, 6, null);
		//row 6
		boardSquares[5][0] = new BoardSquare(5, 0, null);
		boardSquares[5][1] = new RiverSquare(5, 1, null);
		boardSquares[5][2] = new RiverSquare(5, 2, null);
		boardSquares[5][3] = new BoardSquare(5, 3, null);
		boardSquares[5][4] = new RiverSquare(5, 4, null);
		boardSquares[5][5] = new RiverSquare(5, 5, null);
		boardSquares[5][6] = new BoardSquare(5, 6, null);
		//row 7
		boardSquares[6][0] = new BoardSquare(6, 0, null);
		boardSquares[6][1] = new BoardSquare(6, 1, null);
		boardSquares[6][2] = new BoardSquare(6, 2, null);
		boardSquares[6][3] = new BoardSquare(6, 3, null);
		boardSquares[6][4] = new BoardSquare(6, 4, null);
		boardSquares[6][5] = new BoardSquare(6, 5, null);
		boardSquares[6][6] = new BoardSquare(6, 6, null);
		//row 8
		boardSquares[7][0] = new BoardSquare(7, 0, null);
		boardSquares[7][1] = new BoardSquare(7, 1, null);
		boardSquares[7][2] = new BoardSquare(7, 2, null);
		boardSquares[7][3] = new TrapSquare(7, 3, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[7][4] = new BoardSquare(7, 4, null);
		boardSquares[7][5] = new BoardSquare(7, 5, null);
		boardSquares[7][6] = new BoardSquare(7, 6, null);
		//row 9
		boardSquares[8][0] = new BoardSquare(8, 0, null);
		boardSquares[8][1] = new BoardSquare(8, 1, null);
		boardSquares[8][2] = new TrapSquare(8, 2, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[8][3] = new DenSquare(8, 3, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[8][4] = new TrapSquare(8, 4, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[8][5] = new BoardSquare(8, 5, null);
		boardSquares[8][6] = new BoardSquare(8, 6, null);

	}


	private int getValidMoveHorizontal(JungleGamePiece piece, int direction) {
		if (Math.abs(direction)>1)
			direction /= Math.abs(direction);
		
		int columnOffset = direction;
		
		BoardSquare square = getSquareAt(piece.getRow(), piece.getColumn()+columnOffset);
		if (square == null) {
			return 0;
		}
		
		if ( piece.canOccupy(square) ) {
			return columnOffset;
		} else {
			while (square instanceof RiverSquare) {
				if (!square.isEmpty()) {
					return 0;
				}
				columnOffset += direction;
				square = getSquareAt(piece.getRow(), piece.getColumn()+columnOffset);
			}
			//river was clear
			if ( piece.canOccupy(square) ) {
				return columnOffset;
			}			
		}
			
		return 0;
	}
	
	private int getValidMoveVertical(JungleGamePiece piece, int direction) {
		if (Math.abs(direction)>1)
			direction /= Math.abs(direction);
		
		int rowOffset = direction;
		
		BoardSquare square = getSquareAt(piece.getRow()+rowOffset, piece.getColumn());
		if (square == null) {
			return 0;
		}
		
		if ( piece.canOccupy(square) ) {
			return rowOffset;
		} else {
			while (square instanceof RiverSquare) {
				if (!square.isEmpty()) {
					return 0;
				}
				rowOffset += direction;
				square = getSquareAt(piece.getRow()+rowOffset, piece.getColumn());
			}
			//river was clear
			if ( piece.canOccupy(square) ) {
				return rowOffset;
			}			
		}
			
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GameBoard gameBoard = (GameBoard) o;
		return Arrays.equals(boardSquares, gameBoard.boardSquares);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(boardSquares);
	}
}
