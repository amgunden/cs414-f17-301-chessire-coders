package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public class GamesModel {

    private static GamesModel instance = new GamesModel();

    private ObservableList<JungleGame> currentGames;

    private GamesModel() {
        currentGames = FXCollections.observableArrayList();
    }

    public static GamesModel getInstance() {
        if (instance == null) instance = new GamesModel();

        return instance;
    }

    public static void clearInstance() {
        instance.currentGames = null;
        instance = null;
    }

    public void removeGame(long gameID) {
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

    public void addAll(Collection<JungleGame> games) {
        currentGames.addAll(games);
    }
}
