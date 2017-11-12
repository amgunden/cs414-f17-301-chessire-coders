package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerOwnerType;

public class GamePiece {

    private long pieceId;
    private PlayerOwnerType playerOwner;
    private PieceType pieceType;
    private int column;
    private int row;
    private long gameId;

    public GamePiece() {
    }

    public long getPieceId() {
        return pieceId;
    }

    public GamePiece setPieceId(long pieceId) {
        this.pieceId = pieceId;
        return this;
    }

    public PlayerOwnerType getPlayerOwner() {
        return playerOwner;
    }

    public GamePiece setPlayerOwner(PlayerOwnerType playerOwner) {
        this.playerOwner = playerOwner;
        return this;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public GamePiece setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
        return this;
    }

    public int getColumn() {
        return column;
    }

    public GamePiece setColumn(int column) {
        this.column = column;
        return this;
    }

    public int getRow() {
        return row;
    }

    public GamePiece setRow(int row) {
        this.row = row;
        return this;
    }

    public long getGameId() {
        return gameId;
    }

    public GamePiece setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        GamePiece gamePiece = (GamePiece) object;

        if (getPieceId() != gamePiece.getPieceId()) return false;
        if (getColumn() != gamePiece.getColumn()) return false;
        if (getRow() != gamePiece.getRow()) return false;
        if (getGameId() != gamePiece.getGameId()) return false;
        if (getPlayerOwner() != gamePiece.getPlayerOwner()) return false;
        return getPieceType() == gamePiece.getPieceType();
    }

    @Override
    public int hashCode() {
        int result = (int) (getPieceId() ^ (getPieceId() >>> 32));
        result = 31 * result + (getPlayerOwner() != null ? getPlayerOwner().hashCode() : 0);
        result = 31 * result + (getPieceType() != null ? getPieceType().hashCode() : 0);
        result = 31 * result + getColumn();
        result = 31 * result + getRow();
        result = 31 * result + (int) (getGameId() ^ (getGameId() >>> 32));
        return result;
    }
}
