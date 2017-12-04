package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai;

import java.util.Objects;

public class Move {

    private long pieceId;

    private int fromCol;
    private int fromRow;

    private int toCol;
    private int toRow;

    public Move() {
    }

    public long getPieceId() {
        return pieceId;
    }

    public Move setPieceId(long pieceId) {
        this.pieceId = pieceId;
        return this;
    }

    public int getFromCol() {
        return fromCol;
    }

    public Move setFromCol(int fromCol) {
        this.fromCol = fromCol;
        return this;
    }

    public int getFromRow() {
        return fromRow;
    }

    public Move setFromRow(int fromRow) {
        this.fromRow = fromRow;
        return this;
    }

    public int getToCol() {
        return toCol;
    }

    public Move setToCol(int toCol) {
        this.toCol = toCol;
        return this;
    }

    public int getToRow() {
        return toRow;
    }

    public Move setToRow(int toRow) {
        this.toRow = toRow;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return getPieceId() == move.getPieceId() &&
                getFromCol() == move.getFromCol() &&
                getFromRow() == move.getFromRow() &&
                getToCol() == move.getToCol() &&
                getToRow() == move.getToRow();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getPieceId(), getFromCol(), getFromRow(), getToCol(), getToRow());
    }
}
