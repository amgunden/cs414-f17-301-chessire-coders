package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

public class GameBoard {
	//Set up Pieces
	GamePiece[][] gamePieces = new GamePiece[2][8];
	
	//Set up Squares
	BoardSquare[][] boardSquares = new BoardSquare[7][9];

	public GameBoard() {
		setUpBoard();
		setUpPieces();
	}
	
	public GameBoard(GamePiece[][] gamePieces) {
		this.gamePieces = gamePieces;
	}
	
	//the row and column here could be substituted for a direction, thought that would mean a minor loss of clarity.
	public boolean movePiece(PieceType type, PlayerColor color, int row, int column) {
		//WRITE TESTS then construct
		//This method should check the movement with the checkMovement method
		//Then apply the movement to the correct piece.
		
		/*
		for(int i = 0; i < gamePieces.length; i++) {
			if(gamePieces[i].getPieceType().equals(type)) {
				if(gamePieces[i].getColor().equals(color)) {
					if(!checkMovement(type, color, row, column)) {
						return false;
					}
				}
			}
		}
		*/
		return false;
	}
	
	public boolean checkMove(int color, int piece, int row, int column) {
		//This is a basic system for understanding. 
		//finding the piece is dependent on what the UI/group wants. 
		int pieceRow = gamePieces[color][piece].getRow();
		int pieceColumn = gamePieces[color][piece].getColumn();
		
		//Only the row or column can change, one must change.
		if(((row == pieceRow) && (column == pieceColumn))||((row != pieceRow) && (column != pieceColumn))){
			return false;
		}
		
		//The only time the row or column will increase by more than one is if the tiger or leopard is jumping the river
		//Check if it increases by more than one
		if(!(((pieceRow+1 == row)||(pieceRow-1 == row))||((pieceColumn+1 == column)||(pieceColumn-1 == column)))){
			//check if it is jumping the river, which is 3 high and 2 wide.
			if((piece == 4)||(piece == 5)) {
				if((pieceRow == 2) && (row == 6)) {
					
				}
				if((pieceRow == 6) && (row == 2)) {
					
				}
				if((pieceColumn == 0) && (column == 3)) {
					
				}
				if((pieceColumn == 3) && (column == 0)) {
					
				}
				
				
				if((((pieceRow == 2)||(pieceRow == 6))||((pieceColumn+3 == column)||(pieceColumn-3 == column)))){
				if((((pieceRow == row)||(pieceRow-4 == row))||((pieceColumn+3 == column)||(pieceColumn-3 == column)))){
					return false;
				}
			} else {
				return false;
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//If the piece is Tiger or Leopard they can jump water
		if(piece != 4 || piece != 5) {
			if([])
		}
			//Check if row or column was increased by 1
			//If anything else return false
			if (!((row + 1 == pieceRow && column == pieceColumn) || (row - 1 == pieceRow && column == pieceColumn) || (row == pieceRow && column + 1 == pieceColumn) || (row == pieceRow && column + 1 == pieceColumn))){
				return false;
			}
		}
		
		
		//WRITE TESTS then construct
		//This method should find the passed piece then check if the appropriate movement is handled.
		//That is no more than one space moved (unless noted)
		//Not water (unless noted)
		//None of the players pieces occupying the space.
		//No higher power piece occupying the space. 
		return false;		
	}
	
	//Board Setup Functions
	
	private void setUpPieces() {
		//Player 1
		gamePieces[0][0] = new GamePiece(PieceType.Rat, 0, 2, PlayerColor.Red);
		gamePieces[0][1] = new GamePiece(PieceType.Cat, 5, 1, PlayerColor.Red);
		gamePieces[0][2] = new GamePiece(PieceType.Dog, 1, 1, PlayerColor.Red);
		gamePieces[0][3] = new GamePiece(PieceType.Wolf, 4, 2, PlayerColor.Red);
		gamePieces[0][4] = new GamePiece(PieceType.Leopard, 2, 2, PlayerColor.Red);
		gamePieces[0][5] = new GamePiece(PieceType.Tiger, 6, 0, PlayerColor.Red);
		gamePieces[0][6] = new GamePiece(PieceType.Lion, 0, 0, PlayerColor.Red);
		gamePieces[0][7] = new GamePiece(PieceType.Elephant, 6, 2, PlayerColor.Red);		
		//Player 2
		gamePieces[1][0] = new GamePiece(PieceType.Rat, 6, 6, PlayerColor.Black);
		gamePieces[1][1] = new GamePiece(PieceType.Cat, 1, 7, PlayerColor.Black);
		gamePieces[1][2] = new GamePiece(PieceType.Dog, 5, 7, PlayerColor.Black);
		gamePieces[1][3] = new GamePiece(PieceType.Wolf, 2, 6, PlayerColor.Black);
		gamePieces[1][4] = new GamePiece(PieceType.Leopard, 4, 6, PlayerColor.Black);
		gamePieces[1][5] = new GamePiece(PieceType.Tiger, 0, 8, PlayerColor.Black);
		gamePieces[1][6] = new GamePiece(PieceType.Lion, 6, 8, PlayerColor.Black);
		gamePieces[1][7] = new GamePiece(PieceType.Elephant, 0, 6, PlayerColor.Black);
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
