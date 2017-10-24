package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

public class GetPieceLocationResponse {
    private int pieceID;
    private int column;
    private int row;

    public int getPieceID() {
        return pieceID;
    }

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public GetPieceLocationResponse(int pieceID, int column, int row) {
        this.pieceID = pieceID;
        this.column = column;
        this.row = row;
    }
}
