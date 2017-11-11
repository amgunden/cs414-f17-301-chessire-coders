package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.JungleGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GamesManager {

	private static GamesManager instance;
	private ObservableList<JungleGame> games;
	
	private GamesManager() {
		games = FXCollections.observableArrayList();
		games.add(new JungleGame(12345));
	}
	
	public static GamesManager getInstance()
	{
		if (instance == null) {
			instance = new GamesManager();
		}
		
		return instance;
	}
	
	public void createGame()
	{
		// send create game request to server
		// store game with GameID in this.games
		int gameID = 1234;
		games.add(new JungleGame(gameID));
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
