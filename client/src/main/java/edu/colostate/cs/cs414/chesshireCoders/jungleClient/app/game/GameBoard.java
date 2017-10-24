package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game;


public class GameBoard {
	//Set up Pieces
	GamePiece[][] gamePieces = new GamePiece[2][8];

	//Set up Squares
	BoardSquare[][] boardSquares = new BoardSquare[7][9];

	//Variable for rats changing from water and lang
	boolean isAttackingAllowed = true;

	public GameBoard() {
		setUpBoard();
		setUpPieces();
	}

	public GameBoard(GamePiece[][] gamePieces) {
		setUpBoard();
		this.gamePieces = gamePieces;
	}

	//the row and column here could be substituted for a direction, thought that would mean a minor loss of clarity.
	public boolean movePiece(int color, int piece, int column, int row) {
		//Assume piece given is in power level not array value
		piece -= 1;
		
		
		return false;
	}

	//isValidMove will check that the move player's piece is allowed to move to the designated location.
	//color must be 0 (red) or 1 (black)
	//piece must be the powerlevel of the piece (aka Rat = 1 NOT 0)
	//column = X axis location
	//row = Y Axis Location
	public boolean isValidMove(int color, int piece, int column, int row) {	
		//Assume piece given is in power level not array value
		piece -= 1;
		
		if(!(isValidPiece(color, piece)))
			return false;

		if(!(isValidMovement(color, piece, column, row)))
			return false;

		if(!(isPieceAllowedOnSquare(color, piece, column, row)))
			return false;
		
		if(isFriendlyOccupying(color, column, row)){
			return false;
		}		

		if(isEnemyBlocking(color, piece, column, row)){
			return false;
		}

		isAttackingAllowed = true;
		return true;		
	}

	//isValidMove Support Functions ------------------------------------------------------
	private boolean isValidPiece(int color, int piece) {
		if (piece < 0) {
			return false;
		}		

		//Check the piece is not dead
		if(gamePieces[color][piece] == null){
			return false;
		}

		return true;
	}

	private boolean isValidMovement(int color, int piece, int column, int row) {
		//finding the piece is dependent on what the UI/group wants. 
		int pieceColumn = gamePieces[color][piece].getColumn();
		int pieceRow = gamePieces[color][piece].getRow();
		
		//Only the row or column can change, one must change.
		if(((column == pieceColumn) && (row == pieceRow))||((column != pieceColumn) && (row != pieceRow))){
			return false;
		}
		
		//it cannot leave the board
		if((column < 0)||(column > 6))
			return false;
		if((row < 0) || (row > 8))
			return false;

		//The only time the row or column will increase by more than one is if the tiger or leopard is jumping the river
		//Check if it increases by more than one
		if(!(((pieceColumn+1 == column)||(pieceColumn-1 == column))||((pieceRow+1 == row)||(pieceRow-1 == row)))){
			//if it is not a tiger or a leopard return false]
			if(!((piece == 4)||(piece == 5))) {
				return false;
			}
			//if it is not jumping the left river it must jump right.
			if(!(((pieceColumn == 0) && (column == 3)) || ((pieceColumn == 3) && (column == 0)) || ((pieceRow == 2) && (row == 6)) || ((pieceRow == 6) && (row == 2)))) {
				//if it is not jumping the right river return false;
				if(!(((pieceColumn == 3) && (column == 6)) || ((pieceColumn == 6) && (column == 3)) || ((pieceRow == 2) && (row == 6)) || ((pieceRow == 6) && (row == 2)))) {
					return false;
				}
			}

			//If the tiger or leopard is jumping the river there can not be a rat in the way.
			//We first need to check where the animal is starting
			int startingColumn = -1;
			int startingRow = -1; 
			if(pieceColumn < column) {
				startingColumn = pieceColumn;
			}
			if(pieceColumn > column) {
				startingColumn = column;
			}
			if(pieceRow < row) {
				startingRow = row;				
			}
			if(pieceRow > row) {
				startingRow = row;				
			}	

			//Implement through the squares the animal travels to check if the rat occupies the same space.
			if(startingColumn < 0) {
				while(startingColumn < startingColumn+3) {
					if((gamePieces[0][1].getColumn() == startingColumn)	&& (gamePieces[0][1].getRow()==row)) {
						return false;
					}
					if((gamePieces[1][1].getColumn() == startingColumn)	&& (gamePieces[1][1].getRow()==row)) {
						return false;
					}
					startingColumn++;
				}
			} else {
				while(startingRow < startingRow+4) {
					if((gamePieces[0][1].getColumn() == column)	&& (gamePieces[0][1].getRow() == startingRow)) {
						return false;
					}
					if((gamePieces[1][1].getColumn() == column)	&& (gamePieces[1][1].getRow( )== startingRow)) {
						return false;
					}
					startingRow++;
				}
			}
		}

		return true;
	}

	private boolean isPieceAllowedOnSquare(int color, int piece, int column, int row) {	
		GamePiece gamePiece = gamePieces[color][piece];
		BoardSquare boardSquare = boardSquares[column][row];
		//At this point the piece is either moving 1 space or it's a tiger/leopard jumping a river
		//check if it can land on the space. 
		//if it's normal, trap, or den anything can be on it.
		BoardSquare pieceSquare = boardSquares[gamePiece.getColumn()][gamePiece.getRow()];
		isAttackingAllowed = true;
		if(boardSquare.getSquareType().equals(SquareType.River)) {
			//If it's not a normal, trap, or den it must be a river. Only rats can go on river. So if it's not a rat return false.
			if(!(gamePiece.getClass().equals(RatPiece.class))) {
				return false;
			}
			//if is a rat and the rat is not already in the river their can not be a piece there.
			if(pieceSquare.getSquareType().equals(SquareType.River)) {
				isAttackingAllowed = false;
			}
		}	

		return true;
	}

	private boolean isFriendlyOccupying(int color, int column, int row) {
		//if any of the player's pieces are on the space return false;
		for(int i = 0; i < 8; i++) {
			if((gamePieces[color][i].getRow() == row) && (gamePieces[color][i].getColumn() == column)) {
				return true;
			}
		}		

		return false;
	}

	private boolean isEnemyBlocking(int color, int piece, int column, int row) {
		GamePiece gamePiece = gamePieces[color][piece];
		BoardSquare boardSquare = boardSquares[column][row];
		//Check if their is a creature on it with a higher power
		//if the space the piece is moved to is a FRIENDLY trap, any enemy will be consumed so just return true;
		//is it a trap?
		if (boardSquare.getSquareType().equals(SquareType.Trap)){
			//if the trap color equals the piece color it can consume anything so return true.
			if((boardSquare.getColor().equals(PlayerColor.Red)) && (color == 0)){
				return false;
			}
			if((boardSquare.getColor().equals(PlayerColor.Black)) && (color == 1)){
				return false;
			}

		}

		//establish the opposite player;
		int oppositePlayer = 0;
		if(color == 0) {
			oppositePlayer = 1;
		}
		//if the opposite player's piece is on the square check if the power is higher, if so return false
		for(int i = 0; i < 8; i++) {
			if((gamePieces[oppositePlayer][i].getRow() == row) && (gamePieces[0][i].getRow() == column)) {
				if((i > piece)||(!(isAttackingAllowed))) {
					return true;
				}
			}
		}

		return false;
	}

	//Board Setup Functions -----------------------------------------------------------

	private void setUpPieces() {
		//Player 1
		gamePieces[0][0] = new RatPiece(0, 2, PlayerColor.Red);
		gamePieces[0][1] = new CatPiece(5, 1, PlayerColor.Red);
		gamePieces[0][2] = new FoxPiece(1, 1, PlayerColor.Red);
		gamePieces[0][3] = new DogPiece(4, 2, PlayerColor.Red);
		gamePieces[0][4] = new LeopardPiece(2, 2, PlayerColor.Red);
		gamePieces[0][5] = new TigerPiece(6, 0, PlayerColor.Red);
		gamePieces[0][6] = new LionPiece(0, 0, PlayerColor.Red);
		gamePieces[0][7] = new ElephantPiece(6, 2, PlayerColor.Red);		
		//Player 2
		gamePieces[1][0] = new RatPiece(6, 6, PlayerColor.Black);
		gamePieces[1][1] = new CatPiece(1, 7, PlayerColor.Black);
		gamePieces[1][2] = new FoxPiece(5, 7, PlayerColor.Black);
		gamePieces[1][3] = new DogPiece(2, 6, PlayerColor.Black);
		gamePieces[1][4] = new LeopardPiece(4, 6, PlayerColor.Black);
		gamePieces[1][5] = new TigerPiece(0, 8, PlayerColor.Black);
		gamePieces[1][6] = new LionPiece(6, 8, PlayerColor.Black);
		gamePieces[1][7] = new ElephantPiece(0, 6, PlayerColor.Black);
	}

	private void setUpBoard() {		
		//row 1
		boardSquares[0][0] = new BoardSquare(SquareType.Normal);
		boardSquares[1][0] = new BoardSquare(SquareType.Normal);
		boardSquares[2][0] = new BoardSquare(SquareType.Trap, PlayerColor.Red);
		boardSquares[3][0] = new BoardSquare(SquareType.Den, PlayerColor.Red);
		boardSquares[4][0] = new BoardSquare(SquareType.Trap, PlayerColor.Red);
		boardSquares[5][0] = new BoardSquare(SquareType.Normal);
		boardSquares[6][0] = new BoardSquare(SquareType.Normal);
		//row 2
		boardSquares[0][1] = new BoardSquare(SquareType.Normal);
		boardSquares[1][1] = new BoardSquare(SquareType.Normal);
		boardSquares[2][1] = new BoardSquare(SquareType.Normal);
		boardSquares[3][1] = new BoardSquare(SquareType.Trap, PlayerColor.Red);
		boardSquares[4][1] = new BoardSquare(SquareType.Normal);
		boardSquares[5][1] = new BoardSquare(SquareType.Normal);
		boardSquares[6][1] = new BoardSquare(SquareType.Normal);
		//row 3
		boardSquares[0][2] = new BoardSquare(SquareType.Normal);
		boardSquares[1][2] = new BoardSquare(SquareType.Normal);
		boardSquares[2][2] = new BoardSquare(SquareType.Normal);
		boardSquares[3][2] = new BoardSquare(SquareType.Normal);
		boardSquares[4][2] = new BoardSquare(SquareType.Normal);
		boardSquares[5][2] = new BoardSquare(SquareType.Normal);
		boardSquares[6][2] = new BoardSquare(SquareType.Normal);
		//row 4
		boardSquares[0][3] = new BoardSquare(SquareType.Normal);
		boardSquares[1][3] = new BoardSquare(SquareType.River);
		boardSquares[2][3] = new BoardSquare(SquareType.River);
		boardSquares[3][3] = new BoardSquare(SquareType.Normal);
		boardSquares[4][3] = new BoardSquare(SquareType.River);
		boardSquares[5][3] = new BoardSquare(SquareType.River);
		boardSquares[6][3] = new BoardSquare(SquareType.Normal);
		//row 5
		boardSquares[0][4] = new BoardSquare(SquareType.Normal);
		boardSquares[1][4] = new BoardSquare(SquareType.River);
		boardSquares[2][4] = new BoardSquare(SquareType.River);
		boardSquares[3][4] = new BoardSquare(SquareType.Normal);
		boardSquares[4][4] = new BoardSquare(SquareType.River);
		boardSquares[5][4] = new BoardSquare(SquareType.River);
		boardSquares[6][4] = new BoardSquare(SquareType.Normal);
		//row 6
		boardSquares[0][5] = new BoardSquare(SquareType.Normal);
		boardSquares[1][5] = new BoardSquare(SquareType.River);
		boardSquares[2][5] = new BoardSquare(SquareType.River);
		boardSquares[3][5] = new BoardSquare(SquareType.Normal);
		boardSquares[4][5] = new BoardSquare(SquareType.River);
		boardSquares[5][5] = new BoardSquare(SquareType.River);
		boardSquares[6][5] = new BoardSquare(SquareType.Normal);
		//row 7
		boardSquares[0][6] = new BoardSquare(SquareType.Normal);
		boardSquares[1][6] = new BoardSquare(SquareType.Normal);
		boardSquares[2][6] = new BoardSquare(SquareType.Normal);
		boardSquares[3][6] = new BoardSquare(SquareType.Normal);
		boardSquares[4][6] = new BoardSquare(SquareType.Normal);
		boardSquares[5][6] = new BoardSquare(SquareType.Normal);
		boardSquares[6][6] = new BoardSquare(SquareType.Normal);
		//row 8
		boardSquares[0][7] = new BoardSquare(SquareType.Normal);
		boardSquares[1][7] = new BoardSquare(SquareType.Normal);
		boardSquares[2][7] = new BoardSquare(SquareType.Normal);
		boardSquares[3][7] = new BoardSquare(SquareType.Trap, PlayerColor.Black);
		boardSquares[4][7] = new BoardSquare(SquareType.Normal);
		boardSquares[5][7] = new BoardSquare(SquareType.Normal);
		boardSquares[6][7] = new BoardSquare(SquareType.Normal);
		//row 9
		boardSquares[0][8] = new BoardSquare(SquareType.Normal);
		boardSquares[1][8] = new BoardSquare(SquareType.Normal);
		boardSquares[2][8] = new BoardSquare(SquareType.Trap, PlayerColor.Black);
		boardSquares[3][8] = new BoardSquare(SquareType.Den, PlayerColor.Black);
		boardSquares[4][8] = new BoardSquare(SquareType.Trap, PlayerColor.Black);
		boardSquares[5][8] = new BoardSquare(SquareType.Normal);
		boardSquares[6][8] = new BoardSquare(SquareType.Normal);
	}
}
