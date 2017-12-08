package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GameHistoryModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.text.DateFormat;


public class GameHistoryViewImpl extends BaseView {
	
	@FXML
	private Label profileNickName;
	@FXML
	private Label winsNum;
	@FXML
	private Label lossesNum;
	@FXML
	private Label drawsNum;
	@FXML 
	private TableView<Game> historyTable;
	@FXML
	private TableColumn opponent;
	@FXML
	private TableColumn startTime;
	@FXML
	private TableColumn endTime;
	@FXML
	private TableColumn result;
	
	GameHistoryModel history;

    public GameHistoryViewImpl(Observable o) {
		// TODO Auto-generated constructor stub
		history = (GameHistoryModel) o;
	}
	
	public void setLabels() {
		System.out.println(history.getNickname());
		
		profileNickName.setText(history.getNickname());
		winsNum.setText(Integer.toString(history.getWins()));
		lossesNum.setText(Integer.toString(history.getLosses()));
		drawsNum.setText(Integer.toString(history.getDraws()));
		
		opponent.setCellValueFactory(new opponentTableCell() );
		startTime.setCellValueFactory(new startTimeTableCell() );
		endTime.setCellValueFactory(new endTimeTableCell() );
		result.setCellValueFactory(new resultTableCell() );
			
		historyTable.setItems(history.getGames());
		historyTable.getColumns().setAll(opponent, startTime, endTime, result);

		
		
		//historyList.getItems().addAll(history.getGames());

	}

}

class opponentTableCell implements Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>{
	
	AccountModel accountModel = AccountModel.getInstance();
	@Override
	public ObservableValue<String> call(CellDataFeatures<Game, String> game) {
		
    	if (game.getValue().getGameStatus() == GameStatus.DRAW) {
			// Winner & Opponent is Player Two
			if (game.getValue().getPlayerOneNickName().equals(accountModel.getNickName())) return new ReadOnlyStringWrapper( game.getValue().getPlayerTwoNickName());
			// Loser & Opponent is Player One
			else return new ReadOnlyStringWrapper(game.getValue().getPlayerOneNickName());
    		
		} else if (game.getValue().getGameStatus() == GameStatus.WINNER_PLAYER_ONE) {
			// Winner & Opponent is Player Two
			if (game.getValue().getPlayerOneNickName().equals(accountModel.getNickName())) return new ReadOnlyStringWrapper( game.getValue().getPlayerTwoNickName());
			// Loser & Opponent is Player One
			else return new ReadOnlyStringWrapper( game.getValue().getPlayerOneNickName());

		} else if (game.getValue().getGameStatus() == GameStatus.WINNER_PLAYER_TWO) {
			// Winner & Opponent is Player One
			if (game.getValue().getPlayerTwoNickName().equals(accountModel.getNickName())) return new ReadOnlyStringWrapper( game.getValue().getPlayerOneNickName());
			// Loser & Opponent is Player Two
			else return new ReadOnlyStringWrapper( game.getValue().getPlayerTwoNickName());
		}
    	return new ReadOnlyStringWrapper("Error Retrieveing Opponent");
	}
	
}

class startTimeTableCell implements Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>{
	
	AccountModel accountModel = AccountModel.getInstance();
	@Override
	public ObservableValue<String> call(CellDataFeatures<Game, String> game) {
		
    	if( game.getValue().getGameStart() != null) {
    		return new ReadOnlyStringWrapper( DateFormat.getInstance().format(game.getValue().getGameStart()));
    	}
    	else {
    		return new ReadOnlyStringWrapper("Unknown");
    	}
	}
	
}

class endTimeTableCell implements Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>{
	
	AccountModel accountModel = AccountModel.getInstance();
	@Override
	public ObservableValue<String> call(CellDataFeatures<Game, String> game) {
		
    	if( game.getValue().getGameEnd() != null) {
    		return new ReadOnlyStringWrapper( DateFormat.getInstance().format(game.getValue().getGameEnd()));
    	}
    	else {
    		return new ReadOnlyStringWrapper("Unknown");
    	}
	}
	
}

class resultTableCell implements Callback<TableColumn.CellDataFeatures<Game, String>, ObservableValue<String>>{
	
	AccountModel accountModel = AccountModel.getInstance();
	@Override
	public ObservableValue<String> call(CellDataFeatures<Game, String> game) {
		
		if (game.getValue().getGameStatus() == GameStatus.DRAW) {
    		return new ReadOnlyStringWrapper("Draw");    		
		} else if (game.getValue().getGameStatus() == GameStatus.WINNER_PLAYER_ONE) {
			// Winner & Opponent is Player Two
			if (game.getValue().getPlayerOneNickName().equals(accountModel.getNickName())) return new ReadOnlyStringWrapper("Win");
			// Loser & Opponent is Player One
			else return new ReadOnlyStringWrapper("Loss");
			
		} else if (game.getValue().getGameStatus() == GameStatus.WINNER_PLAYER_TWO) {
			// Winner & Opponent is Player One
			if (game.getValue().getPlayerTwoNickName().equals(accountModel.getNickName())) return new ReadOnlyStringWrapper("Win");
			// Loser & Opponent is Player Two
			else return new ReadOnlyStringWrapper("Loss");
		}
		return new ReadOnlyStringWrapper("Error Retrieveing Result");
	}
	
}
