package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GamesModel {

    private static GamesModel instance = new GamesModel();
    private long activeGameId;
    private ObservableList<JungleGame> currentGames;

    private GamesModel() {
        currentGames = FXCollections.observableArrayList();
    }

    public static GamesModel getInstance() {
        return instance;
    }

    public JungleGame getActiveGame() {
        return findById(activeGameId);
    }

    public long getActiveGameId() {
        return activeGameId;
    }

    public void setActiveGameId(long activeGame) {
        this.activeGameId = activeGame;
    }

    public void removeGame(int gameID) {
        // TODO: implement
        // if game is not finished, notify server of game end.
        // remove game locally.
    }

    public void updateOrAddGame(JungleGame jGame) {
        for (int i = 0; i < currentGames.size(); i++) {
            if (currentGames.get(i).getGameID() == jGame.getGameID()) {
                currentGames.set(i, jGame);
                return;
            }
        }
        // If game was not found, add it.
        currentGames.add(jGame);
    }

    public JungleGame findById(long gameID) {
        for (JungleGame jungleGame : currentGames) {
            if (jungleGame.getGameID() == gameID)
                return jungleGame;
        }
        return null;
    }

    public ObservableList<JungleGame> getCurrentGames() {
        return currentGames;
    }
}
