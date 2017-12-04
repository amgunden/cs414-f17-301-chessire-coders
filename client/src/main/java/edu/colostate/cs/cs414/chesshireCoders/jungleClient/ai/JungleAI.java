package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

import java.util.List;
import java.util.Stack;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.WINNER_PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.WINNER_PLAYER_TWO;

public class JungleAI implements Runnable {

    private int bestScore = Integer.MIN_VALUE;
    private Move bestMove;

    private JungleGame game;
    private Stack<Move> movesMade = new Stack<>();

    public Move getBestMove() {
        return bestMove;
    }

    public void findBestMove(JungleGame game) {

        // Iterate through all possible moves
        List<Move> possibleMoves = getAvailableMoves(game);
        for (Move move : possibleMoves) {

            // make the selected move
            pushMove(move);

            // calculate the score for this move using MiniMax
            int score = minimax(game, 0, false, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);

            // If the score is > than the current best, save the score and the associated move.
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            // un-make the move
            popMove();
        }
    }

    private int minimax(JungleGame game, int depth, boolean isMax, double alpha, double beta) {

        if (isMax) {
            int best = Integer.MIN_VALUE;
            best = Math.max(minimax(game, depth + 1, !isMax, alpha, beta), best);
        } else {
            int best = Integer.MAX_VALUE;
            best = Math.max(minimax(game, depth + 1, !isMax, alpha, beta), best);
        }

        return 0;
    }

    private int getScore(JungleGame game, int depth) {
        GameStatus status = game.getGameStatus();
        if (status == WINNER_PLAYER_ONE) return 10 - depth;
        else if (status == WINNER_PLAYER_TWO) return depth - 10;
        else return 0;
    }

    private void pushMove(Move move) {
        // apply move to game
        movesMade.push(move);
    }

    private void popMove() {
        Move move = movesMade.pop();
        // un-apply move from board
    }

    private List<Move> getAvailableMoves(JungleGame game) {
        return null;
    }

    @Override
    public void run() {

    }
}
