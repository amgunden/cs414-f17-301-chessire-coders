package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.LoginHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	private static final String COLOR_VALID = "#BAF5BA";
	
	private static final String COLOR_ERROR = "#F5C3BA";
	
	// matches valid email addresses
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	@FXML
	private Hyperlink hplRegister;
	@FXML
	private Button btnLogin;
	@FXML
	private Label loginErrorMsg;
    @FXML
	private PasswordField passwordField;
    @FXML
	private TextField emailField;
    
	private LoginHandler handler;
	
	public void initialize(URL location, ResourceBundle resources) {
		App.window.setResizable(false);
	}
	
	public void loginClicked()
	{
		System.out.println("btnLogin Clicked.");
		
		System.out.println("Email: " + emailField.getText());
		System.out.println("Password: " + passwordField.getText());
		
		if (validateEmailAddress()) {
			try {
				btnLogin.setDisable(true);

				handler = new LoginHandler(this);
				App.getJungleClient().addListener(handler);

				String email = emailField.getText();
				String password = hashPassword(passwordField.getText());
				handler.sendLogin(email, password);

			} catch (NoSuchAlgorithmException e) {
				System.err.println("Client does not have SHA-256 hashing.");
				loginFailure();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
    public void loginFailure() {
    	if (loginErrorMsg.getText().length() == 0) loginErrorMsg.setText("Login failed.");
        App.getJungleClient().removeListener(handler);
        loginErrorMsg.setVisible(true);
        btnLogin.setDisable(false);
    }

    public void loginFailure(String errorMessage) {
        loginErrorMsg.setPrefWidth(errorMessage.length() * 30);
        loginErrorMsg.setText(errorMessage);
        System.err.println(errorMessage);
        loginFailure();
    }

    public void loginSuccess() {
        try {
            App.getJungleClient().removeListener(handler);
            App.setScene("homePage.fxml");
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
	
	String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return new String(hash);
    }
	
	boolean validateEmailAddress() {
        String email = emailField.getText();
        boolean isEmailValid = EMAIL_PATTERN.matcher(email).matches();
        if (isEmailValid) {
            emailField.setStyle("-fx-control-inner-background: " + COLOR_VALID);
        } else {
            emailField.setStyle("-fx-control-inner-background: " + COLOR_ERROR);
        }
        return isEmailValid;
    }

}
