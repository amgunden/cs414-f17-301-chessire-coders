package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GameHistoryModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui.GameListCell;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui.InviteListCell;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;



public class GameHistoryController{
	
	@FXML
	private Label profileNickName;
	@FXML
	private Label winsNum;
	@FXML
	private Label lossesNum;
	@FXML
	private Label drawsNum;
	@FXML 
	private ListView<Game> historyList;
	
	GameHistoryModel history;
	
	public GameHistoryController(Observable o) {
		// TODO Auto-generated constructor stub
		history = (GameHistoryModel) o;
	}
	
	public void setLabels() {
		System.out.println(history.getNickname());
		profileNickName.setText(history.getNickname());
		winsNum.setText(Integer.toString(history.getWins()));
		lossesNum.setText(Integer.toString(history.getLosses()));
		drawsNum.setText(Integer.toString(history.getDraws()));
		
		historyList.setCellFactory(param -> new GameListCell());
		historyList.setItems(history.getGames());
		//historyList.getItems().addAll(history.getGames());

	}

}
