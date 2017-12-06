package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import java.util.ArrayList;
import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GamesModel {

    private static GamesModel instance = new GamesModel();

    private ObjectProperty<JungleGame> activeGameProperty;
    private ObservableList<JungleGame> currentGames;
    private List<String> availPlayers; // Will hold a list of players who can receive an invitation to a game

	private GamesModel() {
        activeGameProperty = new SimpleObjectProperty<>(null);
        currentGames = FXCollections.observableArrayList();
        availPlayers = new ArrayList<String>();
    }

    public static GamesModel getInstance() {
        return instance;
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
        if (getActiveGame().getGameID() == gameID) setActiveGame(null);
        currentGames.remove(findById(gameID));
    }

    public void updateOrAddGame(JungleGame jGame) {
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
            activeGameProperty.setValue(jGame);
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
    
    public List<String> getAvailPlayers() {
 		return availPlayers;
 	}

 	public void setAvailPlayers(List<String> availPlayers) {
 		this.availPlayers = availPlayers;
 	}
}
