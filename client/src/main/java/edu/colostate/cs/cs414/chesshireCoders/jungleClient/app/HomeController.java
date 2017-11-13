package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.account.AccountHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.GamesManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.InviteManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.NetworkListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.LogoutHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.ui.InviteListCell;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;
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
    private Label lblGameInvites;
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
    private ListView<Invitation> invitationList;
    @FXML
    private VBox mainVBox;
    @FXML
    private StackPane unregSuccess;

    protected ListProperty<JungleGame> gameListProperty = new SimpleListProperty<>();
    protected ListProperty<Invitation> inviteListProperty = new SimpleListProperty<>();
    
    public String nick;
    
    public void initialize(URL location, ResourceBundle resources) {
        App.window.setResizable(false);

        GamesManager.getInstance().setHomeController(this);
        gamesList.itemsProperty().bind(gameListProperty);
        invitationList.itemsProperty().bind(inviteListProperty);
        invitationList.setCellFactory(param -> new InviteListCell());
        btnViewInvites.setVisible(false);
        
        gameListProperty.set(GamesManager.getInstance().getGames());
        inviteListProperty.set(InviteManager.getInstance().getInvites());
        NetworkListener.addEventListeners(App.getJungleClient());
        
    }
    
    public void setNickName(String nick) {
    	this.nick = nick;
    }
    
    public void displayNickname() {
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
        GamesManager.getInstance().createGame(this);
    }
    
    public void initializeBoard(JungleGame game) {
    	Node board;
        try {
            lblActiveGames.setPadding(new Insets(0, 0, 0, 20));
            lblGameInvites.setPadding(new Insets(0, 0, 0, 20));
            
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/gameBoard.fxml"));
            board = loader.load();
            // Get the Controller from the FXMLLoader
            GameBoardController controller = loader.getController();
            controller.setGame(game);
            // Set data in the controller
            borderPane.setCenter(board);
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Game Board.");
        }
    }
    
    public void updateBoard(JungleGame game) {
    	Node board;
        try {
            lblActiveGames.setPadding(new Insets(0, 0, 0, 20));
            lblGameInvites.setPadding(new Insets(0, 0, 0, 20));
            
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/gameBoard.fxml"));
            board = loader.load();
            // Get the Controller from the FXMLLoader
            GameBoardController controller = loader.getController();
            controller.setGame(game);
            // Set data in the controller
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
        Invitation invite = new Invitation(123, 456, 789);
        invite.setSenderNickname("Test Sender");
        InviteManager.getInstance().addInvitation(invite);
    }

    public void viewInvitesClicked() {
        System.out.println("View Invites Clicked.");
    }

    public void printGetUserError(String errorMsg) {
        System.out.println("Error occured while attempting to get User Information: " + errorMsg);
    }
}
