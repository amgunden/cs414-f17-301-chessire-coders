package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui;

import java.text.DateFormat;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import javafx.scene.control.ListCell;

public class GameListCell extends ListCell<Game>{

	public GameListCell() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void updateItem(Game game, boolean b) {
      super.updateItem(game, b);

      if (game != null) {
    	String start = DateFormat.getInstance().format(game.getGameStart());
    	String end = DateFormat.getInstance().format(game.getGameEnd());
    	  
        setText( game.getPlayerTwoNickName()+"	"+start+"	"+end+"	"+game.getGameStatus().toString() );
      }
    }

}
