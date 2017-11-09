package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetPieceLocationRequest extends Session {
    private int pieceID;

    public GetPieceLocationRequest() {
    }

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
