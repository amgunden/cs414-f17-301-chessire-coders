package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;

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

    static void makeMove(Move move, JungleGame game) {
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
        //rows
        for (int i = 0; i < 9; i++) {
            //columns
            for (int j = 0; j < 7; j++) {
                int[] valid = game.getValidMoves(i, j);
                Move move = new Move();
                //m.setPieceId();
                move.setFromRow(i);
                move.setFromCol(j);
                move.setToRow(valid[0] + valid[2] + i);
                move.setToCol(valid[1] + valid[3] + j);
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
