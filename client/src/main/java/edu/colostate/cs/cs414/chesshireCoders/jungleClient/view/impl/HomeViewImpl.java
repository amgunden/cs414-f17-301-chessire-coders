package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.ControllerFactory;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.InvitesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui.InviteListCell;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class HomeViewImpl extends BaseView {

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

    // Get controller instances
    private final HomeController controller = ControllerFactory.getHomeController(this);
    private ListProperty<Invitation> inviteListProperty = new SimpleListProperty<>();
    // Get model instances
    private AccountModel accountModel = AccountModel.getInstance();
    private GamesModel gamesModel = GamesModel.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        App.getWindow().setResizable(false);

        invitationList.itemsProperty().bind(inviteListProperty);
        invitationList.setCellFactory(param -> new InviteListCell(controller));
        btnViewInvites.setVisible(false);
        inviteListProperty.set(InvitesModel.getInstance().getPendingReceivedInvites());

        // Show the players nick name
        nickName.setText(accountModel.getNickName());

        initGamesListView();
        listenForActiveGameChange();
        listenForGameSelectionChange();
    }

    private void logoutClicked() {
        try {
            // Don't wait for server to end session.
            controller.sendLogout();
            controller.dispose();
            App.setScene("loginPage.fxml");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void playClicked() {
        try {
            System.out.println("Start New Game Clicked.");
            controller.sendCreateGame();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void settingsClicked() {
        System.out.println("Settings Clicked.");

        // show context menu
        MenuItem logout = new MenuItem("Log out");
        logout.setOnAction(event -> logoutClicked());
        MenuItem unregister = new MenuItem("Unregister");
        unregister.setOnAction(event -> unregisterClicked());

        ContextMenu settingsMenu = new ContextMenu(logout, unregister);
        Bounds boundsInScreen = btnSettings.localToScreen(btnSettings.getBoundsInLocal());
        settingsMenu.show(btnSettings, boundsInScreen.getMinX(), boundsInScreen.getMaxY());
    }

    private void unregisterClicked() {
        try {
            System.out.println("Unregister Clicked.");
            controller.sendUnregister();
            mainVBox.setEffect(new GaussianBlur());
            unregSuccess.setVisible(true);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void unregSuccessReturnClicked() {
        try {
            System.out.println("btnUnregSuccessReturn Clicked.");
            controller.dispose();
            App.setScene("loginPage.fxml");
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Login page.");
        }
    }

    @FXML
    private void viewGameHistoryClicked() {
        System.out.println("View Game History Clicked.");
        Invitation invite = new Invitation();
        invite.setSenderNickname("Test Sender");
        InvitesModel.getInstance().addInvitation(invite);
    }

    @FXML
    private void viewInvitesClicked() {
        System.out.println("View Invites Clicked.");
    }

    private void listenForGameSelectionChange() {
        gamesList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
		            if (newValue != null) {
		                gamesModel.setActiveGame(newValue);
		            }
                });
    }

    private void initGamesListView() {

        try {
			controller.sendGetActiveGames();
		} catch (IOException e) {
            showError(e.getMessage());
		}
    	
        ObservableList<JungleGame> games = gamesModel.getCurrentGames();
        games.sort((o1, o2) -> (int) (o1.getGameID() - o2.getGameID()));
        // Add a listener to the list in gamesModel to change the list bound to the list view
        // I couldn't update the gamesModel list directly at some point from a non-javafx thread.
        gamesModel.getCurrentGames().addListener((ListChangeListener<JungleGame>) c -> Platform.runLater(() -> {
            while (c.next()) {
            	boolean changed = true;
            	if (c.getRemovedSize() == c.getAddedSize()){
            		changed = false;
            		for (int i = 0; i < c.getRemovedSize(); i++) {
						if (c.getRemoved().get(i).getGameID() == c.getAddedSubList().get(i).getGameID())
							games.set( games.indexOf(c.getRemoved().get(i)), c.getAddedSubList().get(i) );
						else
							changed = true;
					}
            	}
            	
            	if (changed == true) {
            		if (c.wasRemoved()) games.removeAll(c.getRemoved());
            		if (c.wasAdded()) games.addAll(c.getAddedSubList());
            	}
            }
            selectActiveGame();
        }));
        gamesList.setItems(games);
    }

    private void listenForActiveGameChange() {
        gamesModel.getActiveGameProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue != null) {
                reloadActiveGame();
                selectActiveGame();
            } else borderPane.setCenter(null);
        }));
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

    private void reloadActiveGame() {
        Node board;
        try {
            lblActiveGames.setPadding(new Insets(0, 0, 0, 20));
            lblGameInvites.setPadding(new Insets(0, 0, 0, 20));

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/gameBoard.fxml"));
            board = loader.load();

            // Get the Controller from the FXMLLoader
            GameBoardViewImpl gameBoardView = loader.getController();

            // Set the game to load
            gameBoardView.setGame(gamesModel.getActiveGame());

            // Set data in the controller
            borderPane.setCenter(board);
            gameBoardView.checkGameWon();

        } catch (IOException e) {
            showError("ERROR: Unable to load fxml file for Game Board.");
        }
    }
}