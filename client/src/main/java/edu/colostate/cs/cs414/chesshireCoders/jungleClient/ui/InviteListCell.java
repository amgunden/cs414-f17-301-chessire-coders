package edu.colostate.cs.cs414.chesshireCoders.jungleClient.ui;

import java.io.File;
import java.io.FileInputStream;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.InviteManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InviteListCell extends ListCell<Invitation> {

	private Node acceptBtn;
	private Node rejectBtn;  
    private Label lblText;
    private HBox grid;
	
	public InviteListCell() {
        super();    

        setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {              
                // TODO do something                  
            }
        }); 
        
        try {
			File fos = new File("src/main/resources/images/green_check.png");
			acceptBtn = new ImageView(new Image(new FileInputStream(fos)));
			acceptBtn.setPickOnBounds(true);
		} catch (Exception e) {
			acceptBtn = new Button("Accept");
		}
        
        try {
			File fos = new File("src/main/resources/images/red_x.png");
			rejectBtn = new ImageView(new Image(new FileInputStream(fos)));
			rejectBtn.setPickOnBounds(true);
		} catch (Exception e) {
			rejectBtn = new Button("Accept");
		}
        
        acceptBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Invitation invite = getListView().getSelectionModel().getSelectedItem();
            	
                InviteManager.getInstance().acceptInvite(invite);
                getListView().getItems().remove(getItem());
            }
        });
        
        rejectBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Invitation invite = getListView().getSelectionModel().getSelectedItem();
                InviteManager.getInstance().declineInvite(invite);
                getListView().getItems().remove(getItem());
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
            lblText.setText(item.toString());                          
            setGraphic(grid);
        } else {
            setGraphic(null);
        }
    }   
}
