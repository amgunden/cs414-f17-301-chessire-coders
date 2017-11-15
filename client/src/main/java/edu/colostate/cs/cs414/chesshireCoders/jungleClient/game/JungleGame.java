package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.GamesManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.UpdateGameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class JungleGame extends Game {

    protected GameBoard board;
    protected PlayerEnumType viewingPlayer;

    public JungleGame() {
        super();
    }

    public JungleGame(Game game) {
		super();
		setGameEnd(game.getGameEnd());
		setGameID(game.getGameID());
		setGamePieces(game.getGamePieces());
		setGameStart(game.getGameStart());
		setGameStatus(game.getGameStatus());
		setPlayerOneID(game.getPlayerOneID());
		setPlayerTwoID(game.getPlayerTwoID());
		setTurnOfPlayer(game.getTurnOfPlayer());
        board = new GameBoard(getGamePieces()); // TODO initialize with pieces in game.
	}

    public JungleGame(int gameID) {
        super();
        setGameID(gameID);
    }

	public boolean canMovePieceAt(int row, int column) {
        boolean result = false;

        if (this.getGameStatus() == GameStatus.ONGOING && this.getTurnOfPlayer() == viewingPlayer && board.getPieceAt(row, column) != null) {
            if (board.getPieceAt(row, column).getPlayerOwner() == viewingPlayer) {
                result = true;
            }
        }

        return result;
    }

    public void endGame() {

    }

    public int[] getValidMoves(int row, int column) {
        return board.getValidMoves(row, column);
    }

    public PlayerEnumType getViewingPlayer() {
		return viewingPlayer;
	}

    public PlayerEnumType getWinner() {
		List<GamePiece> pieces = getGamePieces();
		int p1Pieces = 0;
		int p2Pieces = 0;
    	
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).getPlayerOwner() == PlayerEnumType.PLAYER_ONE) {
				if (pieces.get(i).getRow() == 8 && pieces.get(i).getColumn()==3)
					return PlayerEnumType.PLAYER_ONE;
				p1Pieces++;
			} else {
				if (pieces.get(i).getRow() == 0 && pieces.get(i).getColumn()==3)
					return PlayerEnumType.PLAYER_TWO;
				p2Pieces++;
			}
		}
		
    	if(p2Pieces == 0) {
			return PlayerEnumType.PLAYER_ONE;
		} else if(p1Pieces == 0) {
			return PlayerEnumType.PLAYER_TWO;
		}
		return null;
	}

    public boolean hasWinner() {
        return false;
    }

    public void movePiece(int[] from, int[] to) {
        board.movePiece(from, to);
        setGamePieces(JungleGamePieceFactory.castPiecesUp(board.getPieces()));
        setTurnOfPlayer((getTurnOfPlayer()==PlayerEnumType.PLAYER_ONE) ? PlayerEnumType.PLAYER_TWO : PlayerEnumType.PLAYER_ONE);
        
        if (getWinner() == PlayerEnumType.PLAYER_ONE) {
        	setGameStatus(GameStatus.WINNER_PLAYER_ONE);
        } else if (getWinner() == PlayerEnumType.PLAYER_TWO) {
        	setGameStatus(GameStatus.WINNER_PLAYER_TWO);
        }
        
        GamesManager.getInstance().updateGame(this);
    }

    public void quitGame() {
        //This method should remove the user requesting it, if the game is not over that user officially loses the game.
    }

	public void setViewingPlayer(PlayerEnumType viewingPlayer) {
		this.viewingPlayer = viewingPlayer;
	}

	public boolean startGame() {
        return true;
    }

	
    @Override
    public String toString() {
        return Long.toString(getGameID());
    }
    
    private void setBoard(List<GamePiece> pieces)
    {
    	this.board.setUpBoard(pieces);;
    }
}
