package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.account.AccountHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.GamesManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.LogoutHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomeController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnLogout;
    @FXML
    private ImageView btnSettings;
    @FXML
    private ImageView btnViewInvites;
    @FXML
    private Button btnViewGameHistory;
    @FXML
    private ListView<JungleGame> gamesList;
    @FXML
    private VBox mainVBox;
    @FXML
    private StackPane unregSuccess;

    protected ListProperty<JungleGame> listProperty = new SimpleListProperty<>();
    
    public void initialize(URL location, ResourceBundle resources) {
        App.window.setResizable(false);
        
        gamesList.itemsProperty().bind(listProperty);

        //This does not work, you can not directly add to a ListProperty
        //listProperty.addAll( asianCurrencyList );
        listProperty.set(GamesManager.getInstance().getGames());
    }

    public void logoutClicked() {
        // Don't wait for server to end session.
        AuthToken token = AuthTokenManager.getInstance().getToken();
        AuthTokenManager.getInstance().setAuthToken(null);

        LogoutHandler handler = new LogoutHandler();
        handler.sendLogout(token);

        try {
            App.setScene("loginPage.fxml");
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for login page.");
        }
    }


    public void playClicked() {
        System.out.println("Start New Game Clicked.");
        GamesManager.getInstance().createGame();
        
        Node board;
        try {
            board = FXMLLoader.load(App.class.getResource("/fxml/gameBoard.fxml"));
            borderPane.setCenter(board);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Game Board.");
        }
    }

    public void settingsClicked() {
        System.out.println("Settings Clicked.");
    }

    public void unregisterClicked() {

        AccountHandler accountHandler = new AccountHandler(this);

        System.out.println("btnUnRegistered Clicked.");

        accountHandler.unregisterUser(this);

        unregisterSuccess();
    }

    public void unregisterSuccess() {

        mainVBox.setEffect(new GaussianBlur());
        unregSuccess.setVisible(true);

    }

    public void UnregSuccessReturnClicked() {

        System.out.println("btnUnregSuccessReturn Clicked.");

        try {
            App.setScene("loginPage.fxml");
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Home page.");
        }
    }


    public void viewGameHistoryClicked() {
        System.out.println("View Game History Clicked.");
    }

    public void viewInvitesClicked() {
        System.out.println("View Invites Clicked.");
    }

    public void printGetUserError(String errorMsg) {
        System.out.println("Error occured while attempting to get User Information: " + errorMsg);
    }

}
