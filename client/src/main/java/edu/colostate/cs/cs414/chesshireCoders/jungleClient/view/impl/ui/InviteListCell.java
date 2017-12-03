package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl.ui;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.IOException;

public class InviteListCell extends ListCell<Invitation> {

    private Node acceptBtn;
    private Node rejectBtn;
    private Label lblText;
    private HBox grid;

    public InviteListCell(HomeController controller) {
        super();

        setOnMouseClicked(event -> {
            // TODO do something
        });

        try {
            File iconImage = new File("src/main/resources/images/green_check.png");
            acceptBtn = new ImageView(iconImage.toURI().toString());
            acceptBtn.setPickOnBounds(true);
        } catch (Exception e) {
            acceptBtn = new Button("Accept");
        }

        try {
            File iconImage = new File("src/main/resources/images/red_x.png");
            rejectBtn = new ImageView(iconImage.toURI().toString());
            rejectBtn.setPickOnBounds(true);
        } catch (Exception e) {
            rejectBtn = new Button("Reject");
        }

        acceptBtn.setOnMouseClicked(event -> {
            try {
                Invitation invite = getListView().getSelectionModel().getSelectedItem();
                controller.sendAcceptInvite(invite);
                getListView().getItems().remove(getItem());
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Show dialog instead of stack trace
            }
        });

        rejectBtn.setOnMouseClicked(event -> {
            try {
                Invitation invite = getListView().getSelectionModel().getSelectedItem();
                controller.sendRejectInvite(invite);
                getListView().getItems().remove(getItem());
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Show dialog instead of stack trace
            }
        });

        lblText = new Label();
        grid = new HBox();
        Pane pane = new Pane();
        grid.getChildren().addAll(lblText, pane, acceptBtn, rejectBtn);
        HBox.setHgrow(pane, Priority.ALWAYS);
        setText(null);
    }

    @Override
    public void updateItem(Invitation item, boolean empty) {
        super.updateItem(item, empty);
        setEditable(false);
        if (item != null) {
            lblText.setText(String.format("From: %s", item.getSenderNickname()));
            setGraphic(grid);
        } else {
            setGraphic(null);
        }
    }
}
