package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class RegisterController implements Initializable {

	@FXML
	private Hyperlink hplLogin;
	
	@FXML Button btnRegister;
	
	public void loginClicked()
	{
		try {
			App.setScene("loginPage.fxml");
		} catch (IOException e) {
			System.err.println("ERROR: Unable to load fxml file for Login page.");
		}
	}
	
	public void registerClicked()
	{
		System.out.println("btnRegister Clicked.");
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
