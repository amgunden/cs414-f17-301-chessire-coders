package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class GetPieceLocationRequest extends Session {
    int pieceID;

    public GetPieceLocationRequest(String accessToken, int pieceID) {
        super(accessToken);
        this.pieceID = pieceID;
    }
}
