package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

import java.util.List;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.WINNER_PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.WINNER_PLAYER_TWO;

public class NegaMax extends MoveFinder {

    NegaMax(JungleGame game, int maxDepth) {
        super(game, maxDepth);
    }

    /**
     * Negamax with alpha-beta pruning
     */
    private double negaMax_AB(int depth, double alpha, double beta) {

        GameStatus status = game.getGameStatus();
        // Base case: a player has won, or maximum depth reached. Return it's score.
        if (status == WINNER_PLAYER_ONE || status == WINNER_PLAYER_TWO || depth == maxDepth) {
            return getUtility(depth);
        }
        // Otherwise select best possible move
        else {
            double currentBestScore = Double.NEGATIVE_INFINITY;

            // for each available move
            List<Move> moves = getAvailableMoves(game);
            for (Move move : moves) {
                // make the selected move
                makeMove(move, game);
                // get the score from making this move
                double score = -negaMax_AB(depth + 1, -beta, -alpha);
                // revert the board to it's original state
                unMakeMove(move, game);

                if (score == 0.0) continue;

                // get the best move of the best move so far, and the current move
                if (score > currentBestScore) {
                    currentBestScore = score;
                    bestMove = move;
                }

                alpha = Math.max(alpha, score);

                // Prune the rest of the branches
                if (alpha >= beta) break;
            }
            return currentBestScore;
        }
    }

    /**
     * Assumes PLAYER_TWO is the AI.
     */
    private double getUtility(int depth) {
        GameStatus status = game.getGameStatus();
        if (status == WINNER_PLAYER_ONE) return (-1.0 / depth);
        else if (status == WINNER_PLAYER_TWO) return (1.0 / depth);
        else return 0.0; // No winner
    }

    /**
     * This finds the best move to make and assigns it to the instance variable 'bestMove'
     */
    @Override
    protected void findBestMove() {
        negaMax_AB(maxDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
