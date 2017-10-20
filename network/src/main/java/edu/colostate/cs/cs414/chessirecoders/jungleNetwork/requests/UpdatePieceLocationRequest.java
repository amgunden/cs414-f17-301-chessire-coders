package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.requests;

public class UpdatePieceLocationRequest extends Session {
    int pieceID;
    int x;
    int y;

    public UpdatePieceLocationRequest(String accessToken, int pieceID, int x, int y) {
        super(accessToken);
        this.pieceID = pieceID;
        this.x = x;
        this.y = y;
    }
}
