package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveFinder implements Runnable {

    protected final JungleGame game;
    final int maxDepth;
    Move bestMove;

    MoveFinder(JungleGame game, int maxDepth) {
        this.game = game;
        this.maxDepth = maxDepth;
    }

    public static MoveFinder getMoveFinder(JungleGame game, AlgorithmType algorithm) {
        return getMoveFinder(game, 100, algorithm);
    }

    public static MoveFinder getMoveFinder(JungleGame game, int maxDepth, AlgorithmType algorithm) {
        switch (algorithm) {

            case NEGA_MAX:
                return new NegaMax(game, maxDepth);

            default:
                throw new IllegalArgumentException("Good job, you broke it.");
        }
    }

    public static void makeMove(Move move, JungleGame game) {
        int from[] = new int[2];
        int to[] = new int[2];

        from[0] = move.getFromRow();
        from[1] = move.getFromCol();

        to[0] = move.getToRow();
        to[1] = move.getToCol();

        game.movePiece(from, to);
    }

    static void unMakeMove(Move move, JungleGame game) {
        int from[] = new int[2];
        int to[] = new int[2];

        from[0] = move.getToRow();
        from[1] = move.getToCol();

        to[0] = move.getFromRow();
        to[1] = move.getFromCol();

        game.movePiece(from, to);
    }

    static List<Move> getAvailableMoves(JungleGame game) {

        List<Move> moves = new ArrayList<>();

        for (GamePiece piece : game.getGamePieces()) {
            if (piece.getPlayerOwner() != game.getTurnOfPlayer()) continue;

            int[] valid = game.getValidMoves(piece.getRow(), piece.getColumn());
            // 0 = LEFT, 1 = UP, 2 = RIGHT, 3 = DOWN
            for (int i = 0; i < valid.length; i++) {
                // set initial columns
                int fromCol = piece.getColumn();
                int toCol = piece.getColumn();

                // set initial rows
                int fromRow = piece.getRow();
                int toRow = piece.getRow();

                // apply direction
                int directionDist = valid[i];
                if (i == 0) {
                    toCol += directionDist;
                } else if (i == 1) {
                    toRow += directionDist;
                } else if (i == 2) {
                    toCol += directionDist;
                } else if (i == 3) {
                    toRow += directionDist;
                }

                if (toCol == 0 && toRow == 0) continue;

                // Create and populate the move object
                Move move = new Move();
                move.setFromCol(fromCol);
                move.setToCol(toCol);
                move.setFromRow(fromRow);
                move.setToRow(toRow);
                moves.add(move);
            }
        }
        return moves;
    }

    protected abstract void findBestMove();

    public Move getBestMove() {
        if (bestMove == null) throw new IllegalStateException("MoveFinder has not been run.");
        return bestMove;
    }

    @Override
    public void run() {
        findBestMove();
    }

    public enum AlgorithmType {
        NEGA_MAX
    }
}
