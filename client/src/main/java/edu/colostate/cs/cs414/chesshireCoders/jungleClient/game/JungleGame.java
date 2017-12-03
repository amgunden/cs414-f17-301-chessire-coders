package edu.colostate.cs.cs414.chesshireCoders.jungleClient.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import java.util.List;
import java.util.Objects;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.*;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_TWO;

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
        setPlayerOneNickName(game.getPlayerOneNickName());
        setPlayerTwoNickName(game.getPlayerTwoNickName());
		setTurnOfPlayer(game.getTurnOfPlayer());
        board = new GameBoard(getGamePieces()); // TODO initialize with pieces in game.
	}

    public JungleGame(int gameID) {
        super();
        setGameID(gameID);
    }

	public boolean canMovePieceAt(int row, int column) {
        boolean result = false;

        if (this.getGameStatus() == ONGOING && this.getTurnOfPlayer() == viewingPlayer && board.getPieceAt(row, column) != null) {
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
            if (pieces.get(i).getPlayerOwner() == PLAYER_ONE) {
				if (pieces.get(i).getRow() == 8 && pieces.get(i).getColumn()==3)
                    return PLAYER_ONE;
				p1Pieces++;
			} else {
				if (pieces.get(i).getRow() == 0 && pieces.get(i).getColumn()==3)
                    return PLAYER_TWO;
				p2Pieces++;
			}
		}
		
    	if(p2Pieces == 0) {
            return PLAYER_ONE;
		} else if(p1Pieces == 0) {
            return PLAYER_TWO;
		}
		return null;
	}

    public boolean hasWinner() {
        return false;
    }

    public void movePiece(int[] from, int[] to) {
        board.movePiece(from, to);
        setGamePieces(JungleGamePieceFactory.castPiecesUp(board.getPieces()));
        setTurnOfPlayer((getTurnOfPlayer() == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE);

        if (getWinner() == PLAYER_ONE) {
            setGameStatus(WINNER_PLAYER_ONE);
        } else if (getWinner() == PLAYER_TWO) {
            setGameStatus(WINNER_PLAYER_TWO);
        }
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

        String opposingPlayer = this.viewingPlayer == PLAYER_ONE ? getPlayerTwoNickName() : getPlayerOneNickName();

        if (getGameStatus() == PENDING) {
            return String.format("%d - PENDING", getGameID());
        } else if (getGameStatus() == ONGOING) {
            String turnString = String.format("(%s turn)", this.viewingPlayer == getTurnOfPlayer() ? "your" : "their");
            return String.format("%d - VS. %s %s", getGameID(), opposingPlayer, turnString);
        } else if (getGameStatus() == WINNER_PLAYER_ONE || getGameStatus() == WINNER_PLAYER_TWO) {
            String winner = getGameStatus() == WINNER_PLAYER_ONE ? getPlayerOneNickName() : getPlayerTwoNickName();
            return String.format("%d - VS. %s (WINNER: %s)", getGameID(), opposingPlayer, winner);
        } else { // Draw
            return String.format("%d - VS. %s (DRAW)", getGameID(), opposingPlayer);
        }
    }
    
    private void setBoard(List<GamePiece> pieces)
    {
        this.board.setUpBoard(pieces);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JungleGame that = (JungleGame) o;
        return Objects.equals(board, that.board) &&
                getViewingPlayer() == that.getViewingPlayer();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), board, getViewingPlayer());
    }
}
