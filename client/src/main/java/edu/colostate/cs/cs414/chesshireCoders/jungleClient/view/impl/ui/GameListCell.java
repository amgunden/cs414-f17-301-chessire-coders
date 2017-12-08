package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui;

import java.text.DateFormat;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import javafx.scene.control.ListCell;

public class GameListCell extends ListCell<Game>{
	
	// Get model instances
    private AccountModel accountModel = AccountModel.getInstance();

	public GameListCell() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void updateItem(Game game, boolean b) {
      super.updateItem(game, b);

      if (game != null) {
    	String start = DateFormat.getInstance().format(game.getGameStart());
    	String end = DateFormat.getInstance().format(game.getGameEnd());
    	
    	String result = findResult(game);
    	String opponent = findOpponentPlayer(game);
    	  
        setText( opponent+"   "+start+"    "+end+"    "+result );
      }
    }
	
	public String findResult(Game game) {
    	if (game.getGameStatus() == GameStatus.DRAW) {
    		return "Draw";    		
		} else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_ONE) {
			// Winner & Opponent is Player Two
			if (game.getPlayerOneNickName().equals(accountModel.getNickName())) return "Win";
			// Loser & Opponent is Player One
			else return "Loss";
			
		} else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_TWO) {
			// Winner & Opponent is Player One
			if (game.getPlayerTwoNickName().equals(accountModel.getNickName())) return "Win";
			// Loser & Opponent is Player Two
			else return "Loss";
		}
		return "Error Retrieveing Result";
		
	}
	
	public String findOpponentPlayer(Game game) {
    	if (game.getGameStatus() == GameStatus.DRAW) {
			// Winner & Opponent is Player Two
			if (game.getPlayerOneNickName().equals(accountModel.getNickName())) return game.getPlayerTwoNickName();
			// Loser & Opponent is Player One
			else return game.getPlayerTwoNickName();
    		
		} else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_ONE) {
			// Winner & Opponent is Player Two
			if (game.getPlayerOneNickName().equals(accountModel.getNickName())) return game.getPlayerTwoNickName();
			// Loser & Opponent is Player One
			else return game.getPlayerTwoNickName();

		} else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_TWO) {
			// Winner & Opponent is Player One
			if (game.getPlayerTwoNickName().equals(accountModel.getNickName())) return game.getPlayerOneNickName();
			// Loser & Opponent is Player Two
			else return game.getPlayerTwoNickName();
		}
    	return "Error Retrieveing Opponent";
	}

}
