package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.account.*;

public class RegisterController implements Initializable {

	@FXML
	private Hyperlink hplLogin;
	
	@FXML Button btnRegister;

	@FXML
	private TextField emailField;

	@FXML
	private TextField nickNameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private PasswordField passwordReenterField;
	
	@FXML
	private Label alreadyRegistered;
	
	
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
		AccountHandler accountHandler = new AccountHandler();

		System.out.println("btnRegistered Clicked.");
		
		System.out.println("Email: " + emailField.getText());
		System.out.println("Password: " + passwordField.getText());
		
		// Commenting out register validation so UI can be run
		
		// accountHandler.registerUser(emailField.getText(), nickNameField.getText(),passwordField.getText(), passwordReenterField.getText(), this);
				
		registrationSuccess();
		//System.out.println("btnRegister Clicked.");
	}
	
	public void registrationSuccess() {
		try {
			App.setScene("homePage.fxml");
		} catch (IOException e) {
			System.err.println("ERROR: Unable to load fxml file for Home page.");
		} 
	}
	
	public void registrationFailure()
	{
		alreadyRegistered.setVisible(true);
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO Auto-generated method stub

	}

}
