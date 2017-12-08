package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.ControllerFactory;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GameHistoryModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.InvitesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui.InviteListCell;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

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
    private ImageView btnViewOthersGameHistory;
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

    // GameBoard view
    private GameBoardViewImpl gameBoardView;

    private boolean colorblind = false;

    @FXML
    public void initialize() {
        App.getWindow().setResizable(false);

        invitationList.itemsProperty().bind(inviteListProperty);
        invitationList.setCellFactory(param -> new InviteListCell(controller));
        btnViewInvites.setVisible(false);
        inviteListProperty.set(InvitesModel.getInstance().getPendingReceivedInvites());

        // Show the players nick name
        nickName.setText(accountModel.getNickName());

        initGamesListView();
        listenForGameSelectionChange();
        getOngoingGames();
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

    private void colorblindClicked() {
        colorblind = !colorblind;
        loadGame(getSelectedGame());
    }

    private void showInstructions() {
        Node board;
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/instructions.fxml"));
            board = loader.load();
            borderPane.setCenter(board);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        MenuItem colorblind = new MenuItem("Toggle Colorblind Mode");
        colorblind.setOnAction(event -> colorblindClicked());
        MenuItem instructions = new MenuItem("Show Instructions");
        instructions.setOnAction(event -> showInstructions());

        ContextMenu settingsMenu = new ContextMenu(logout, unregister, colorblind, instructions);
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
            System.err.println("ERROR: Unable to load fxml file for Home page.");
        }
    }

    @FXML
    private void viewGameHistoryClicked() {
        System.out.println("View Game History Clicked.");
        GameHistoryModel historyModel = new GameHistoryModel(this.nickName.getText());
        getGameHistory(this.nickName.getText(), historyModel);


    }

    @FXML
    private void viewOthersGameHistoryClicked() {

        System.out.println("View Others Game History Clicked.");
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Enter players nickname:");
        dialog.setHeaderText("View Player Profile");
        Optional<String> opponentNickname = dialog.showAndWait();
        if (opponentNickname.isPresent() && !opponentNickname.get().isEmpty()) {
            GameHistoryModel historyModel = new GameHistoryModel(opponentNickname.get());
            getGameHistory(opponentNickname.get(), historyModel);
        }

    }

    private void getGameHistory(String nickName, GameHistoryModel historyModel) {
        historyModel.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
                // TODO Display pop up;
                Platform.runLater(() -> {

                    final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profilePage.fxml"));
                    GameHistoryViewImpl controller = new GameHistoryViewImpl(o);
                    loader.setController(controller);

                    try {
                        Parent root;
                        root = loader.load();
                        final Scene scene = new Scene(root, 566, 413);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        //stage.initOwner(emailField.getScene().getWindow());
                        stage.setScene(scene);
                        stage.show();
                        controller.setLabels();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                });
                System.out.println("Show game history");
            }
        });

        try {
            controller.sendGetUserGameHistory(nickName, historyModel);
        } catch (Exception e) {
            showError(e.getMessage());
        }
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
                        loadGame(getSelectedGame());
                    }
                });
    }

    private void initGamesListView() {

        ObservableList<JungleGame> games = FXCollections.observableArrayList();
        // Wrap the list in a sorted list.
        SortedList<JungleGame> sortedGames = new SortedList<>(games, (o1, o2) -> (int) (o1.getGameID() - o2.getGameID()));

        // Add a listener to the list in gamesModel to change the list bound to the list view
        // I couldn't update the gamesModel list directly at some point from a non-javafx thread.
        gamesModel.getCurrentGames().addListener((ListChangeListener<JungleGame>) c -> Platform.runLater(() -> {
            while (c.next()) {
                if (c.wasReplaced()) {
                    int selectedIndex = gamesList.getSelectionModel().getSelectedIndex();
                    games.removeAll(c.getRemoved());
                    games.addAll(c.getAddedSubList());
                    selectGame(selectedIndex);
                } else if (c.wasAdded()) {
                    games.addAll(c.getAddedSubList());
                    if (c.getAddedSubList().size() == 1) selectGame(c.getAddedSubList().get(0));
                } else if (c.wasRemoved()) {
                    games.removeAll(c.getRemoved());
                    loadGame(getSelectedGame());
                }
            }
        }));
        gamesList.setItems(sortedGames);
    }

    private void getOngoingGames() {
        try {
            controller.sendGetActiveGames();
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

    private JungleGame getSelectedGame() {
        return gamesList.getSelectionModel().getSelectedItem();
    }

    private void selectGame(int selectedIndex) {
        gamesList.requestFocus();
        gamesList.getSelectionModel().select(selectedIndex);
        gamesList.getFocusModel().focus(selectedIndex);
    }

    private void selectGame(JungleGame game) {
        if (game != null) {
            selectGame(gamesList.getItems().indexOf(game));
        }
    }

    private void loadGame(JungleGame game) {
        if (game == null) {
            borderPane.setCenter(null);
            return;
        }

        Node board;
        try {
            lblActiveGames.setPadding(new Insets(0, 0, 0, 20));
            lblGameInvites.setPadding(new Insets(0, 0, 0, 20));

            String boardgameName = "/fxml/gameBoard.fxml";
            if (colorblind)
                boardgameName = "/fxml/gameBoard_colorblind.fxml";
            FXMLLoader loader = new FXMLLoader(App.class.getResource(boardgameName));
            board = loader.load();

            // cleanup the old board view
            if (gameBoardView != null) gameBoardView.dispose();
            // Get the new Controller from the FXMLLoader
            gameBoardView = loader.getController();

            // Set the game to load
            if (colorblind)
                gameBoardView.setColorblind();
            gameBoardView.setGame(game);

            // Set data in the controller
            borderPane.setCenter(board);
            gameBoardView.checkGameWon();

        } catch (IOException e) {
            showError("ERROR: Unable to load fxml file for Game Board.");
        }
    }
}
