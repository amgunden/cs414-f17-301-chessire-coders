package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameHistoryModel implements Observable{

    private ObservableList<Game> pastGames;
    private String nickname;
    private int wins;
    private int losses;
    private int draws;
    private ArrayList<InvalidationListener> listeners;

    public GameHistoryModel(String nickname) {
        listeners = new ArrayList<>();
        this.nickname = nickname;
    }
    
    @Override
	public void addListener(InvalidationListener listener) {
		listeners.add(listener);
	}
    
    public int getDraws() {
    	return draws;
    }
    
    public int getLosses() {
    	return losses;
    }
    
    public ObservableList<Game> getGames() {
    	return pastGames;
    }
    
    public int getWins() {
    	return wins;
    }

	public void removeAllListeners() {
		listeners.clear();
	}
	
	@Override
	public void removeListener(InvalidationListener listener) {
		listeners.remove(listener);
	}
	
	public void setPastGames(List<Game> games)
    {
        pastGames = FXCollections.observableArrayList(games);
        wins = losses = draws = 0;
        
        for (Iterator<Game> iterator = games.iterator(); iterator.hasNext();) {
			Game game = iterator.next();
        	
        	if (game.getGameStatus() == GameStatus.DRAW) {
				draws++;
			} else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_ONE) {
				if (game.getPlayerOneNickName().equals(nickname)) wins++;
				else losses++;
			} else if (game.getGameStatus() == GameStatus.WINNER_PLAYER_TWO) {
				if (game.getPlayerTwoNickName().equals(nickname)) wins++;
				else losses++;
			}
		}
        
        notifyListeners();
    }
	
	private void notifyListeners() {
		for (Iterator<InvalidationListener> iterator = listeners.iterator(); iterator.hasNext();) {
			iterator.next().invalidated(this);
		}
	}
}
