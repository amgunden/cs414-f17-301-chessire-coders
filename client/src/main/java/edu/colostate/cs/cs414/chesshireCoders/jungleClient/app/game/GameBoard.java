package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

public class GameBoard {

	//Set up Squares
	BoardSquare[][] boardSquares = new BoardSquare[9][7];

	public GameBoard() {
		setUpBoard();
	}

	public GamePiece getPieceAt(int row, int col) {
		if ( (col < 0) || (col > 6) || (row < 0) || (row > 8) )
			return null;
		
		return getSquareAt(row, col).getPiece();
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
		
		GamePiece piece = getPieceAt(row, column);
		
		validMoves[0] = getValidMoveHorizontal(piece, -1); // left
		validMoves[1] = getValidMoveVertical(piece, -1); // up
		validMoves[2] = getValidMoveHorizontal(piece, 1); // right
		validMoves[3] = getValidMoveVertical(piece, 1); // down
		
		return validMoves;
	}
	
	//the row and column here could be substituted for a direction, thought that would mean a minor loss of clarity.
	public void movePiece(int[] from, int[] to) {
		BoardSquare fromSquare = getSquareAt(from[0],from[1]);
		BoardSquare toSquare = getSquareAt(to[0],to[1]);
		
		toSquare.setPiece( fromSquare.getPiece() );
		fromSquare.clearPiece();
	}
	
	private int getValidMoveHorizontal(GamePiece piece, int direction) {
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
	
	private int getValidMoveVertical(GamePiece piece, int direction) {
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


	//Board Setup Functions -----------------------------------------------------------

	private void setUpBoard() {		
		//row 1
		boardSquares[0][0] = new BoardSquare(0, 0, new LionPiece(0, 0, PlayerColor.Black));
		boardSquares[0][1] = new BoardSquare(0, 1, null);
		boardSquares[0][2] = new TrapSquare(0, 2, null, PlayerColor.Black);
		boardSquares[0][3] = new DenSquare(0, 3, null, PlayerColor.Black);
		boardSquares[0][4] = new TrapSquare(0, 4, null, PlayerColor.Black);
		boardSquares[0][5] = new BoardSquare(0, 5, null);
		boardSquares[0][6] = new BoardSquare(0, 6, new TigerPiece(6, 0, PlayerColor.Black));
		//row 2
		boardSquares[1][0] = new BoardSquare(1, 0, null);
		boardSquares[1][1] = new BoardSquare(1, 1, new DogPiece(1, 1, PlayerColor.Black));
		boardSquares[1][2] = new BoardSquare(1, 2, null);
		boardSquares[1][3] = new TrapSquare(1, 3, null, PlayerColor.Black);
		boardSquares[1][4] = new BoardSquare(1, 4, null);
		boardSquares[1][5] = new BoardSquare(1, 5, new CatPiece(5, 1, PlayerColor.Black));
		boardSquares[1][6] = new BoardSquare(1, 6, null);
		//row 3
		boardSquares[2][0] = new BoardSquare(2, 0, new RatPiece(0, 2, PlayerColor.Black));
		boardSquares[2][1] = new BoardSquare(2, 1, null);
		boardSquares[2][2] = new BoardSquare(2, 2, new LeopardPiece(2, 2, PlayerColor.Black));
		boardSquares[2][3] = new BoardSquare(2, 3, null);
		boardSquares[2][4] = new BoardSquare(2, 4, new FoxPiece(4, 2, PlayerColor.Black));
		boardSquares[2][5] = new BoardSquare(2, 5, null);
		boardSquares[2][6] = new BoardSquare(2, 6, new ElephantPiece(6, 2, PlayerColor.Black));
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
		boardSquares[6][0] = new BoardSquare(6, 0, new ElephantPiece(0, 6, PlayerColor.Red));
		boardSquares[6][1] = new BoardSquare(6, 1, null);
		boardSquares[6][2] = new BoardSquare(6, 2, new FoxPiece(2, 6, PlayerColor.Red));
		boardSquares[6][3] = new BoardSquare(6, 3, null);
		boardSquares[6][4] = new BoardSquare(6, 4, new LeopardPiece(4, 6, PlayerColor.Red));
		boardSquares[6][5] = new BoardSquare(6, 5, null);
		boardSquares[6][6] = new BoardSquare(6, 6, new RatPiece(6, 6, PlayerColor.Red));
		//row 8
		boardSquares[7][0] = new BoardSquare(7, 0, null);
		boardSquares[7][1] = new BoardSquare(7, 1, new CatPiece(1, 7, PlayerColor.Red));
		boardSquares[7][2] = new BoardSquare(7, 2, null);
		boardSquares[7][3] = new TrapSquare(7, 3, null, PlayerColor.Red);
		boardSquares[7][4] = new BoardSquare(7, 4, null);
		boardSquares[7][5] = new BoardSquare(7, 5, new DogPiece(5, 7, PlayerColor.Red));
		boardSquares[7][6] = new BoardSquare(7, 6, null);
		//row 9
		boardSquares[8][0] = new BoardSquare(8, 0, new TigerPiece(0, 8, PlayerColor.Red));
		boardSquares[8][1] = new BoardSquare(8, 1, null);
		boardSquares[8][2] = new TrapSquare(8, 2, null, PlayerColor.Red);
		boardSquares[8][3] = new DenSquare(8, 3, null, PlayerColor.Red);
		boardSquares[8][4] = new TrapSquare(8, 4, null, PlayerColor.Red);
		boardSquares[8][5] = new BoardSquare(8, 5, null);
		boardSquares[8][6] = new BoardSquare(8, 6, new LionPiece(6, 8, PlayerColor.Red));

	}
}
