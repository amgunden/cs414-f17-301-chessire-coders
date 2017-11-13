package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class GameBoard {

	//Set up Squares
	BoardSquare[][] boardSquares = new BoardSquare[9][7];
	int p1Pieces = 8;
	int p2Pieces = 8;

	public GameBoard() {
		setUpBoard();
	}

	public JungleGamePiece getPieceAt(int row, int col) {
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
		
		JungleGamePiece piece = getPieceAt(row, column);
		
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
				
		//if a player has no remaining pieces the game is over
		if(toSquare.getPiece()!=null) {
			if(toSquare.getPiece().getPlayerOwner().equals(PlayerColor.Red)){ // TODO fix
				p1Pieces -= 1;
			} else {
				p2Pieces -= 1;
			}
		}
		
		toSquare.setPiece( fromSquare.getPiece() );
		fromSquare.clearPiece();
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

	//These are holder functions until JungleGame.java is set up. 
	public boolean isGameOver() {		
		//if either player has one the game is over.
		return (hasP1Won() || hasP2Won());
	}
	
	public PlayerColor getWinner() {
		if(hasP1Won()) {
			return PlayerColor.Red;
		} else if(hasP2Won()) {
			return PlayerColor.Black;
		}
		return null;
	}
	
	private boolean hasP1Won() {
		//if p2 is out of pieces p1 wins
		if(p2Pieces == 0) {
			return true;
		}
		//or if p1 is in p2's den p1 wins
		if(getPieceAt(0, 3) != null) {
			return true;
		}
		return false;
	}
	
	private boolean hasP2Won() {
		//if p1 is out of pieces p2 wins
		if(p1Pieces == 0) {
			return true;
		}
		//or if p2 is in p1's den p2 wins
		if(getPieceAt(7, 3) != null) {
			return true;
		}		
		return false;
	}
	
	//Board Setup Functions -----------------------------------------------------------

	private void setUpBoard() {		
		//row 1
		boardSquares[0][0] = new BoardSquare(0, 0, new LionPiece(PlayerEnumType.PLAYER_TWO, 0, 0));
		boardSquares[0][1] = new BoardSquare(0, 1, null);
		boardSquares[0][2] = new TrapSquare(0, 2, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[0][3] = new DenSquare(0, 3, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[0][4] = new TrapSquare(0, 4, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[0][5] = new BoardSquare(0, 5, null);
		boardSquares[0][6] = new BoardSquare(0, 6, new TigerPiece(PlayerEnumType.PLAYER_TWO, 6, 0));
		//row 2
		boardSquares[1][0] = new BoardSquare(1, 0, null);
		boardSquares[1][1] = new BoardSquare(1, 1, new DogPiece(PlayerEnumType.PLAYER_TWO, 1, 1));
		boardSquares[1][2] = new BoardSquare(1, 2, null);
		boardSquares[1][3] = new TrapSquare(1, 3, null, PlayerEnumType.PLAYER_TWO);
		boardSquares[1][4] = new BoardSquare(1, 4, null);
		boardSquares[1][5] = new BoardSquare(1, 5, new CatPiece(PlayerEnumType.PLAYER_TWO, 5, 1));
		boardSquares[1][6] = new BoardSquare(1, 6, null);
		//row 3
		boardSquares[2][0] = new BoardSquare(2, 0, new RatPiece(PlayerEnumType.PLAYER_TWO, 0, 2));
		boardSquares[2][1] = new BoardSquare(2, 1, null);
		boardSquares[2][2] = new BoardSquare(2, 2, new LeopardPiece(PlayerEnumType.PLAYER_TWO, 2, 2));
		boardSquares[2][3] = new BoardSquare(2, 3, null);
		boardSquares[2][4] = new BoardSquare(2, 4, new FoxPiece(PlayerEnumType.PLAYER_TWO, 4, 2));
		boardSquares[2][5] = new BoardSquare(2, 5, null);
		boardSquares[2][6] = new BoardSquare(2, 6, new ElephantPiece(PlayerEnumType.PLAYER_TWO, 6, 2));
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
		boardSquares[6][0] = new BoardSquare(6, 0, new ElephantPiece(PlayerEnumType.PLAYER_ONE, 0, 6));
		boardSquares[6][1] = new BoardSquare(6, 1, null);
		boardSquares[6][2] = new BoardSquare(6, 2, new FoxPiece(PlayerEnumType.PLAYER_ONE, 2, 6));
		boardSquares[6][3] = new BoardSquare(6, 3, null);
		boardSquares[6][4] = new BoardSquare(6, 4, new LeopardPiece(PlayerEnumType.PLAYER_ONE, 4, 6));
		boardSquares[6][5] = new BoardSquare(6, 5, null);
		boardSquares[6][6] = new BoardSquare(6, 6, new RatPiece(PlayerEnumType.PLAYER_ONE, 6, 6));
		//row 8
		boardSquares[7][0] = new BoardSquare(7, 0, null);
		boardSquares[7][1] = new BoardSquare(7, 1, new CatPiece(PlayerEnumType.PLAYER_ONE, 1, 7));
		boardSquares[7][2] = new BoardSquare(7, 2, null);
		boardSquares[7][3] = new TrapSquare(7, 3, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[7][4] = new BoardSquare(7, 4, null);
		boardSquares[7][5] = new BoardSquare(7, 5, new DogPiece(PlayerEnumType.PLAYER_ONE, 5, 7));
		boardSquares[7][6] = new BoardSquare(7, 6, null);
		//row 9
		boardSquares[8][0] = new BoardSquare(8, 0, new TigerPiece(PlayerEnumType.PLAYER_ONE, 0, 8));
		boardSquares[8][1] = new BoardSquare(8, 1, null);
		boardSquares[8][2] = new TrapSquare(8, 2, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[8][3] = new DenSquare(8, 3, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[8][4] = new TrapSquare(8, 4, null, PlayerEnumType.PLAYER_ONE);
		boardSquares[8][5] = new BoardSquare(8, 5, null);
		boardSquares[8][6] = new BoardSquare(8, 6, new LionPiece(PlayerEnumType.PLAYER_ONE, 6, 8));

	}
}
