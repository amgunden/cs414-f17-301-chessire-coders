package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.Game;

public class GameBoard {
	GamePiece[] gamePieces = new GamePiece[16];

	public GameBoard() {
		//Player 1
		gamePieces[0] = new GamePiece(PieceType.Rat, 0, 2);
		gamePieces[1] = new GamePiece(PieceType.Cat, 5, 1);
		gamePieces[2] = new GamePiece(PieceType.Dog, 1, 1);
		gamePieces[3] = new GamePiece(PieceType.Wolf, 4, 2);
		gamePieces[4] = new GamePiece(PieceType.Leopard, 2, 2);
		gamePieces[5] = new GamePiece(PieceType.Tiger, 6, 0);
		gamePieces[6] = new GamePiece(PieceType.Lion, 0, 0);
		gamePieces[7] = new GamePiece(PieceType.Elephant, 6, 2);
		
		//Player 2
		gamePieces[8] = new GamePiece(PieceType.Rat, 6, 6);
		gamePieces[9] = new GamePiece(PieceType.Cat, 1, 7);
		gamePieces[10] = new GamePiece(PieceType.Dog, 5, 7);
		gamePieces[11] = new GamePiece(PieceType.Wolf, 2, 6);
		gamePieces[12] = new GamePiece(PieceType.Leopard, 4, 6);
		gamePieces[13] = new GamePiece(PieceType.Tiger, 0, 8);
		gamePieces[14] = new GamePiece(PieceType.Lion, 6, 8);
		gamePieces[15] = new GamePiece(PieceType.Elephant, 0, 6);
	}
	
	public GameBoard(GamePiece[] gamePieces) {
		this.gamePieces = gamePieces;
	}

}
