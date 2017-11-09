package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;

public class GetPieceResponse extends Response {

    private GamePiece gamePiece;

    public GetPieceResponse() {
    }

    public GetPieceResponse(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }

    public GetPieceResponse(int statusCode, String errMsg, GamePiece gamePiece) {
        super(statusCode, errMsg);
        this.gamePiece = gamePiece;
    }

    public GamePiece getGamePiece() {
        return gamePiece;
    }

    public void setGamePiece(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }
}
