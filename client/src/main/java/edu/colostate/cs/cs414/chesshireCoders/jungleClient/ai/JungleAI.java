package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

import java.util.List;
import java.util.Stack;

import com.esotericsoftware.kryo.util.IdentityMap.Entry;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.WINNER_PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.WINNER_PLAYER_TWO;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_ONE;

public class JungleAI implements Runnable {

    private static final GameStatus ONGOING = null;
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
    /*
     * Negmax with alpha -beta pruning
     */
    private  static Entry<Double,Move> negaMax_AB(JungleGame game, int depthLeft, Double alpha, Double betha) {
    	double bestValue=0;
    	Move bestMove=null;
    	Entry<Double,Move> result = null;
    	result.key=bestValue;
    	result.value=bestMove;
    	GameStatus status = game.getGameStatus();
    	if (status ==WINNER_PLAYER_ONE|| status == WINNER_PLAYER_TWO) {
    		result.key=getUtility(game);
    		result.value=null;
    		return  result;
    	}
    	else {
    		bestValue= Double.NEGATIVE_INFINITY;
    		bestMove= null;
    		List <Move> moves= getAvailableMoves(game);
    		for (int i=0; i< moves.size(); i++) {
    			double value=0;
    			makeMove(moves.get(i),game);
    			result= negaMax_AB(game, depthLeft-1, -betha, -alpha);
    			value=result.key;
    			value=-value;
    			unMakeMove(moves.get(i), game);
    			if (result.key== null) continue;
    			
    			bestValue = Math.max(bestValue,value);
		        bestMove=moves.get(i);
		        alpha =  Math.max(alpha,value);
		        //Prunning the rest of the branches
		        if (alpha >= betha) break;
    		}
    		
    	}
    	
    	return result;
    }
    private static Double getUtility(JungleGame game) {
    	Double utility = null;
    	GameStatus status=game.getGameStatus();
    	if (status==WINNER_PLAYER_ONE) {
    		if (game.getTurnOfPlayer()== PLAYER_ONE) {
    			return (double) 1;
    		}else{
    			return (double) -1; 
    		}
    		
    	}else {
    		if(status==WINNER_PLAYER_TWO) {
    			if (game.getTurnOfPlayer()!= PLAYER_ONE) {
        			return (double) 1;
        		}else{
        			return (double) -1; 
        		}
    			
    		}else {
    			return null;
    		}	
    	}
    }
    
    private static void makeMove(Move move, JungleGame game) {
    	int from[]=null;
    	int to[]=null;
    	from[0]=move.getFromRow();
    	from[1]=move.getFromCol();
    	to[0]=move.getToRow();
    	to[1]=move.getToCol();
    	game.movePiece(from, to);
    }
    private static void unMakeMove(Move move, JungleGame game) {
    	int from[]=null;
    	int to[]=null;
    	to[0]=move.getFromRow();
    	to[1]=move.getFromCol();
    	from[0]=move.getToRow();
    	from[1]=move.getToCol();
    	game.movePiece(from, to);
    	
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

    private static List<Move> getAvailableMoves(JungleGame game) {
    List<Move> moves = null;
    int valid[]=null;
    //rows
	for (int i=0; i<9; i++) {
		//columns
		for (int j=0; j< 7; j++) {
			valid=game.getValidMoves(i, j);
			Move move=new Move();
			//m.setPieceId();
			move.setFromRow(i);
			move.setFromCol(j);
			move.setToRow(valid[0]+valid[2]+i);
			move.setToCol(valid[1]+valid[3]+j);
			moves.add(move);			
		}
		
	}
    	return moves;
    }

    @Override
    public void run() {
    	JungleGame game=new JungleGame();
    	Entry<Double,Move> result=null;
    	Move move;
    	
    	while(game.getGameStatus()==ONGOING){
    		//bot
    		result= negaMax_AB(game,100,Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    		move=result.value;
    		makeMove(move, game);
    		
    		//person
    		if (game.getGameStatus()==ONGOING) {
    			//changePlayer(game);
    			//ask user for move
    			//change player
    		}
    		
    	}
    }

	private boolean gameOver() {
		// TODO Auto-generated method stub
		return false;
	}
}
