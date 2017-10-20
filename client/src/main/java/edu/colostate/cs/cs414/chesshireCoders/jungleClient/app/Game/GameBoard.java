package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

public class GameBoard {
	//Set up Pieces
	GamePiece[] gamePieces = new GamePiece[16];
	
	//Set up Squares
	BoardSquare[][] boardSquares = new BoardSquare[7][9];

	public GameBoard() {
		setUpBoard();
		setUpPieces();
	}
	
	public GameBoard(GamePiece[] gamePieces) {
		this.gamePieces = gamePieces;
	}
	
	public boolean movePiece(PieceType type, PlayerColor color, int row, int column) {
		if(!checkMovement(type, color, row, column)) {
			return false;
		}
		for(int i = 0; i < gamePieces.length; i++) {
			if(gamePieces[i].getPieceType().equals(type)) {
				if(gamePieces[i].getColor())
			}
		}
		return false;
	}
	
	public boolean checkMovement(PieceType type, PlayerColor color, int row, int column) {
		return false;		
	}
	
	//Board Setup Functions
	
	private void setUpPieces() {
		//Player 1
		gamePieces[0] = new GamePiece(PieceType.Rat, 0, 2, PlayerColor.Red);
		gamePieces[1] = new GamePiece(PieceType.Cat, 5, 1, PlayerColor.Red);
		gamePieces[2] = new GamePiece(PieceType.Dog, 1, 1, PlayerColor.Red);
		gamePieces[3] = new GamePiece(PieceType.Wolf, 4, 2, PlayerColor.Red);
		gamePieces[4] = new GamePiece(PieceType.Leopard, 2, 2, PlayerColor.Red);
		gamePieces[5] = new GamePiece(PieceType.Tiger, 6, 0, PlayerColor.Red);
		gamePieces[6] = new GamePiece(PieceType.Lion, 0, 0, PlayerColor.Red);
		gamePieces[7] = new GamePiece(PieceType.Elephant, 6, 2, PlayerColor.Red);		
		//Player 2
		gamePieces[8] = new GamePiece(PieceType.Rat, 6, 6, PlayerColor.Black);
		gamePieces[9] = new GamePiece(PieceType.Cat, 1, 7, PlayerColor.Black);
		gamePieces[10] = new GamePiece(PieceType.Dog, 5, 7, PlayerColor.Black);
		gamePieces[11] = new GamePiece(PieceType.Wolf, 2, 6, PlayerColor.Black);
		gamePieces[12] = new GamePiece(PieceType.Leopard, 4, 6, PlayerColor.Black);
		gamePieces[13] = new GamePiece(PieceType.Tiger, 0, 8, PlayerColor.Black);
		gamePieces[14] = new GamePiece(PieceType.Lion, 6, 8, PlayerColor.Black);
		gamePieces[15] = new GamePiece(PieceType.Elephant, 0, 6, PlayerColor.Black);
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
