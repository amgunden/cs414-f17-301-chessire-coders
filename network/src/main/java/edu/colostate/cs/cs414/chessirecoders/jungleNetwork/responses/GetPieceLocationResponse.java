package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.responses;

public class GetPieceLocationResponse {
    int pieceID;
    int x;
    int y;

    public GetPieceLocationResponse(int pieceID, int x, int y) {
        this.pieceID = pieceID;
        this.x = x;
        this.y = y;
    }
}
