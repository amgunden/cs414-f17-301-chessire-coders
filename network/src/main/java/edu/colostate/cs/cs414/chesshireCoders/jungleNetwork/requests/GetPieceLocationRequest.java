package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class GetPieceLocationRequest extends Session {
    private int pieceID;

    public GetPieceLocationRequest(String accessToken, int pieceID) {
        super(accessToken);
        this.pieceID = pieceID;
    }

    public int getPieceID() {
        return pieceID;
    }

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }
}
