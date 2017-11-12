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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomeController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lblActiveGames;
    @FXML
    private ImageView btnSettings;
    @FXML
    private ImageView btnViewInvites;
    @FXML
    private ImageView btnViewGameHistory;
    @FXML
    private Label nickName;
    @FXML
    private ListView<JungleGame> gamesList;
    @FXML
    private VBox mainVBox;
    @FXML
    private StackPane unregSuccess;

    protected ListProperty<JungleGame> listProperty = new SimpleListProperty<>();
    
    public String nick;
    
    public void initialize(URL location, ResourceBundle resources) {
        App.window.setResizable(false);
        //displayNickName();
        gamesList.itemsProperty().bind(listProperty);

        //This does not work, you can not directly add to a ListProperty
        //listProperty.addAll( asianCurrencyList );
        listProperty.set(GamesManager.getInstance().getGames());
    }
    
    public void setNickName(String nick) {
    	this.nick = nick;
    }
    
    public void displayNickName() {
    	nickName.setText(nick);
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
            lblActiveGames.setPadding(new Insets(0, 0, 0, 20));
            borderPane.setCenter(board);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Game Board.");
        }
    }

    public void settingsClicked() {
        System.out.println("Settings Clicked.");
        // show context menu
        MenuItem logout = new MenuItem("Log out");
        logout.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                logoutClicked();
            }
        });
        MenuItem unregister = new MenuItem("Unregister");
        unregister.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                unregisterClicked();
            }
        });
        
        ContextMenu settingsMenu = new ContextMenu(logout, unregister);
        Bounds boundsInScreen = btnSettings.localToScreen(btnSettings.getBoundsInLocal());
        settingsMenu.show(btnSettings, boundsInScreen.getMinX(), boundsInScreen.getMaxY());
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
