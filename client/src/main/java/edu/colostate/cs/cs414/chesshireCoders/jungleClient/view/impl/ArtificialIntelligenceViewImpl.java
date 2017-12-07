package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.ControllerFactory;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.io.IOException;



public class ArtificialIntelligenceViewImpl extends BaseView {
	
	@FXML
    private ListView<JungleGame> gamesList;
	
    private final LoginController controller = ControllerFactory.getLoginController(this);
    private final AccountModel accountModel = AccountModel.getInstance();
    private GamesModel gamesModel = GamesModel.getInstance();


    
    private final ChangeListener<Boolean> loginSuccessListener = (observable, oldValue, newValue) -> {
        if (newValue != null && newValue) loginSuccess();
        else if (newValue != null) loginFailure();
    };


    public void initialize() {
        listenForLoginSuccess();
    }
    
    private void login() {
        System.out.println("AI Login .");

        System.out.println("Email: " + "AI@email.com");
        System.out.println("Password: " + "AIpass");
        try {
            String email = "AI@email.com";
            String password = Crypto.hashSHA256("AIpass".getBytes());
            controller.sendLogin(email, password);
        } catch (Exception e) {
            showError(e.getMessage());
            //if (loginErrorMsg.getText().length() == 0) loginErrorMsg.setText("Login failed.");
        }
        
    }
    private void listenForLoginSuccess() {
        accountModel.loginSuccessProperty().addListener(loginSuccessListener);
    }
    private void loginSuccess() {
        Platform.runLater(() -> {
            try {
                cleanup();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/homePage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                App.setScene(scene);
            } catch (IOException e) {
                System.err.println("ERROR: Unable to load fxml file for Home page.");
            }
        });
    }

    private void loginFailure() {
    }
    private void cleanup() {
        accountModel.loginSuccessProperty().removeListener(loginSuccessListener);
        controller.dispose();
    }
    
    
    
    
    private void initGamesListView() {
        // Some voodoo magic I don't fully understand, but it works!

        ObservableList<JungleGame> games = FXCollections.observableArrayList();
        // Wrap the list in a sorted list.
        SortedList<JungleGame> sortedGames = new SortedList<>(games, (o1, o2) -> (int) (o1.getGameID() - o2.getGameID()));

        // Add a listener to the list in gamesModel to change the list bound to the list view
        // I couldn't update the gamesModel list directly at some point from a non-javafx thread.
        gamesModel.getCurrentGames().addListener((ListChangeListener<JungleGame>) c -> Platform.runLater(() -> {
            while (c.next()) {
                if (c.wasRemoved()) games.removeAll(c.getRemoved());
                if (c.wasAdded()) games.addAll(c.getAddedSubList());
            }
            selectActiveGame();
        }));
        gamesList.setItems(sortedGames);
    }
    
    private void selectActiveGame() {
        JungleGame game = gamesModel.getActiveGame();
        if (game != null) {
            int index = gamesList.getItems().indexOf(game);
            gamesList.requestFocus();
            gamesList.getSelectionModel().select(index);
            gamesList.getFocusModel().focus(index);
        }
    }

	
	
	

}
