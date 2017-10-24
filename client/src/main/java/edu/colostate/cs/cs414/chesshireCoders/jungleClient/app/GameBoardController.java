package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.net.URL;
import java.util.ResourceBundle;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.GameBoard;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.PlayerColor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

	private int[] start;
	private GameBoard board;
	
	@FXML
	private GridPane gridPane;
	
	public void initialize(URL location, ResourceBundle resources) {
		board = new GameBoard();
		start = new int[2];
	}
	
	
	@FXML
	public void squareClicked(MouseEvent event)
	{
		StackPane square = (StackPane) event.getSource();
		int clickColumn = GridPane.getColumnIndex(square);
		int clickRow = GridPane.getRowIndex(square);

		System.out.println("Square ("+clickRow+","+clickColumn+") Clicked.");
		
		// if square is not highlighted
		if (square.getBackground() == null)
		{
			removePreviousHighlights();
			GamePiece piece = board.getPieceAt(clickColumn, clickRow);
			
			if (piece != null && piece.getColor() == PlayerColor.Red)
			{
				highlightStartSquare(square, clickColumn, clickRow);
				highlightMoves(clickColumn, clickRow);
			}
		}
		// if square is highlighted
		else
		{
			if (clickRow != start[0] || clickColumn != start[1])
			{
				movePiece(clickColumn, clickRow);
				removePreviousHighlights();
			}
		}
		
	}



	private void highlightStartSquare(StackPane square, int c, int r) {
		start[0] = r; start[1] = c;
		
		Color yellow = Color.rgb(150, 150, 0, 0.65);
		setHighlight(square, yellow);
	}



	private void highlightMoves(int c, int r) {
		int[] moves = board.getValidMoves(r, c);
		
		Color green = Color.rgb(0, 150, 0, 0.65);
		if (moves[0]!=0) setHighlight(getSquare(r, c+moves[0]), green);
		if (moves[1]!=0) setHighlight(getSquare(r+moves[1], c), green);
		if (moves[2]!=0) setHighlight(getSquare(r, c+moves[2]), green);
		if (moves[3]!=0) setHighlight(getSquare(r+moves[3], c), green);
	}



	private void movePiece(int c, int r) {
		StackPane fromSquare = getSquare(start[0], start[1]);
		StackPane toSquare = getSquare(r,c);
		ObservableList<Node> imageViews1 = fromSquare.getChildren();
		ObservableList<Node> imageViews2 = toSquare.getChildren();
		ImageView piece = (ImageView) imageViews1.remove(imageViews1.size()-1);
		
		if (imageViews2.size() > 1) {
			imageViews2.remove(imageViews2.size()-1);
		}
		
		toSquare.getChildren().add(piece);
		board.movePiece(start, new int[]{r, c});
	}

	/**
	 * Sets the background highlight of a square in the GridPane.
	 * @param square the Stackpane to be highlighted.
	 * @param fill the color of the highlight. A value of null removes the highlight.
	 */
	private void setHighlight(StackPane square, Paint fill) {
		if (square == null) return;
		
		int padding = (fill == null) ? 0 : 6;
		
		for (Node n : square.getChildren())
		{
			ImageView image = (ImageView) n;
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

	/**
	 * Removes the highlight from the previously highlighted square and it's surroundings.
	 */
	private void removePreviousHighlights() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 7; c++) {
				setHighlight(getSquare(r, c), null);
			}
		}
	}

	/**
	 * Returns the square located at (row, column) in the GridPane.
	 * @param row the row index of the square to return.
	 * @param column the column index of the square to return.
	 * @return Stackpane located at (row, column).
	 */
	private StackPane getSquare(int row, int column) {
		if ( (row < 0) || (row > 8) || (column < 0) || (column > 6) )
			return null;
		
		return (StackPane) gridPane.getChildren().get(7*row + column);
	}

}
