package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class LoginController implements Initializable {

	@FXML
	private Hyperlink hplRegister;
	
	@FXML
	private Button btnLogin;
	
	public void loginClicked()
	{
		System.out.println("btnLogin Clicked.");
		try {
			App.setScene("gameBoard.fxml");
		} catch (IOException e) {
			System.err.println("ERROR: Unable to load fxml file for Home page.");
		}
	}
	
	public void registerClicked()
	{
		try {
			App.setScene("registerPage.fxml");
		} catch (IOException e) {
			System.err.println("ERROR: Unable to load fxml file for Register page.");
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
