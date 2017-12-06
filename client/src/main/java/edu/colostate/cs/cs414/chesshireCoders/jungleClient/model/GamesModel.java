package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GamesModel {

    private static GamesModel instance = new GamesModel();

    private ObjectProperty<JungleGame> activeGameProperty;
    private ObservableList<JungleGame> currentGames;

    private GamesModel() {
        activeGameProperty = new SimpleObjectProperty<>(null);
        currentGames = FXCollections.observableArrayList();
    }

    public static GamesModel getInstance() {
        if (instance == null) instance = new GamesModel();
        
    	return instance;
    }
    
    public static void clearInstance() {
        Platform.runLater(()->{
            getInstance().activeGameProperty = null;
            getInstance().currentGames = null;
            instance = null;
    	});
    }

    public ObjectProperty<JungleGame> getActiveGameProperty() {
        return activeGameProperty;
    }

    public JungleGame getActiveGame() {
        return activeGameProperty.getValue();
    }

    public void setActiveGame(JungleGame game) {
        activeGameProperty.setValue(game);
    }

    public void removeGame(long gameID) {
    	Platform.runLater(()->{
	    	if (getActiveGame().getGameID() == gameID) setActiveGame(null);
	        currentGames.remove(findById(gameID));
    	});
    }

    public void updateOrAddGame(JungleGame jGame) {
    	Platform.runLater(()->{
	    	// Find a local game with the same Id.
	        boolean found = false;
	        for (int i = 0; i < currentGames.size(); i++) {
	            if (currentGames.get(i).getGameID() == jGame.getGameID()) {
	                found = true;
	                currentGames.set(i, jGame); // Overwrite that game.
	                break;
	            }
	        }
	        // If game was not found, add it.
	        if (!found) currentGames.add(jGame);
	
	        // Update the active game reference if necessary
	        JungleGame activeGame = activeGameProperty.get();
	        if (activeGame != null && jGame.getGameID() == activeGame.getGameID())
            setActiveGame(jGame);
    	});
    }

    public JungleGame findById(long gameID) {
        for (JungleGame jungleGame : currentGames) {
            if (jungleGame.getGameID() == gameID)
                return jungleGame;
        }
        return null;
    }
    
    public boolean hasGame(long gameID) {
        for (JungleGame jungleGame : currentGames) {
            if (jungleGame.getGameID() == gameID)
                return true;
        }
        return false;
    }

    public ObservableList<JungleGame> getCurrentGames() {
        return currentGames;
    }
}