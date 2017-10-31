package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;

public class GamePiece {

    private int pieceId;
    private int playerId;
    private PieceType pieceType;
    private int column;
    private int row;
    private int gameId;

    public GamePiece() {
    }

    public GamePiece(int pieceId, int playerId, PieceType pieceType, int column, int row, int gameId) {
        this.pieceId = pieceId;
        this.playerId = playerId;
        this.pieceType = pieceType;
        this.column = column;
        this.row = row;
        this.gameId = gameId;
    }

    public GamePiece(int playerId, PieceType pieceType, int column, int row, int gameId) {
        this.playerId = playerId;
        this.pieceType = pieceType;
        this.column = column;
        this.row = row;
        this.gameId = gameId;
    }

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
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

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
