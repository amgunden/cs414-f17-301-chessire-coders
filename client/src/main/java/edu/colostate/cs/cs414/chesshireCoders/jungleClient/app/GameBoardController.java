package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameBoardController implements Initializable {

	private int[] from;
	
	@FXML
	public GridPane gridPane;
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		from = new int[2];
	}
	
	@FXML
	public void squareClicked(MouseEvent event)
	{
		StackPane square = (StackPane) event.getSource();
		int c = GridPane.getColumnIndex(square);
		int r = GridPane.getRowIndex(square);

		System.out.println("Square ("+r+","+c+") Clicked.");
		
		// if square is not highlighted
		if (square.getBackground() == null)
		{
			// remove highlight from "from"
			removePreviousHighlights();
			// TODO if square contains piece that belongs to player
				Color yellow = Color.rgb(150, 150, 0, 0.65);
				setHighlight(square, yellow);
				from[0] = r; from[1] = c;
				// TODO get legal moves
				// TODO highlight legal move squares
				//Color green = Color.rgb(0, 150, 0, 0.65);
				//setHighlight(getSquare(r, c+1), green);
				//setHighlight(getSquare(r, c-1), green);
		}
		// if square is highlighted
		else
		{
			if (from[0] != r || from[1] != c)
			{
				//int[] to = {r,c};
				// TODO initiate move (piece, from, to)
			}
		}
		
	}

	private void setHighlight(StackPane square, Paint fill) {
		int padding = (fill == null) ? 0 : 6;
		
		if (square.getChildren().size() > 0)
		{
			ImageView image = (ImageView) square.getChildren().get(0);
			image.setFitWidth(80-2*padding); image.setFitHeight(80-2*padding);
		}
		square.setPadding(new Insets(padding));
		
		if (fill == null)
		{
			square.setBackground(null);
		}
		else
		{
			square.setBackground(new Background(new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY)));	
		}
	}

	private void removePreviousHighlights() {
		int r = from[0]; int c = from[1];
		setHighlight(getSquare(r, c), null);
		
		if (r > 0)
			setHighlight(getSquare(r-1, c), null);
		if (r < 8)
			setHighlight(getSquare(r+1, c), null);
		if (c > 0)
			setHighlight(getSquare(r, c-1), null);
		if (c < 6)
			setHighlight(getSquare(r, c+1), null);
	}

	private StackPane getSquare(int row, int column) {
		return (StackPane) gridPane.getChildren().get(7*row + column);
	}

}
