package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.CreateGameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.GetGameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GamesManager {

	private static GamesManager instance;
	private ObservableList<JungleGame> games;
	private HomeController homeController;
	
	private GamesManager() {
		games = FXCollections.observableArrayList();
	}
	
	public static GamesManager getInstance()
	{
		if (instance == null) {
			instance = new GamesManager();
		}
		
		return instance;
	}
	
	public void createGame(HomeController controller)
	{
		this.homeController = controller;
		// send create game request to server
		CreateGameHandler handler = new CreateGameHandler();
		App.getJungleClient().addListener(handler);
		handler.sendCreateGame();
	}
	
	public void createGame(long gameID)
	{
    	GetGameHandler handler = new GetGameHandler();
		App.getJungleClient().addListener(handler);
		handler.sendGetGame(gameID);
	}
	
	public void createGame(Game game)
	{
		// store game with GameID in this.games
		JungleGame jGame = new JungleGame(game);
		games.add(jGame);
		homeController.initializeBoard(jGame);
	}
	
	public void fetchGames()
	{
		// TODO Fetch the user's games from the server.
	}
	
	public ObservableList<JungleGame> getGames()
	{
		return games;
	}
	
	public void removeGame(int gameID)
	{
		// if game is not finished, notify server of game end.
		// remove game locally.
	}
	
}
