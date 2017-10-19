package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameBoardController implements Initializable {

	@FXML
	public GridPane gridPane;
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void squareClicked(MouseEvent event)
	{
		StackPane square = (StackPane) event.getSource();
		int c = gridPane.getColumnIndex(square);
		int r = gridPane.getRowIndex(square);

		System.out.println("Square ("+r+","+c+") Clicked.");
	}

}
