package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.network.RegistrationHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    @FXML
    private FlowPane flowPane;

    @FXML
    private Hyperlink hplLogin;

    @FXML
    Button btnRegister;

    @FXML
    public TextField emailField;

    @FXML
    public TextField nickNameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public PasswordField passwordReenterField;

    @FXML
    private Label alreadyRegistered;

    @FXML
    private Label regFailed;

    private RegistrationHandler handler;

    private boolean registrationSuccess = false;

    private static final String COLOR_VALID = "#BAF5BA";
    private static final String COLOR_ERROR = "#F5C3BA";
    // matches valid email addresses
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // Usernames must start with a letter, followed by any number of alpha-numeric characters
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Z]+[A-Z0-9]*$", Pattern.CASE_INSENSITIVE);
    // matches a string containing at least 6 alpha-numeric, or punctuation characters.
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Z0-9\\p{Punct}]{6,}?$", Pattern.CASE_INSENSITIVE);

    public void loginClicked() {
        try {
            App.setScene("loginPage.fxml");
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Login page.");
        }
    }

    public void registerClicked() {

        System.out.println("btnRegistered Clicked.");

        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + passwordField.getText());

        boolean isRegistrationValid = validateRegistrationInfo();

        if (isRegistrationValid) {


            try {
                flowPane.setDisable(true);

                handler = new RegistrationHandler(this);
                App.getJungleClient().addListener(handler);

                String email = emailField.getText();
                String nickName = nickNameField.getText();
                String password = Crypto.hashSHA256(passwordField.getText().getBytes());
                handler.sendRegistration(email, nickName, password);

            } catch (NoSuchAlgorithmException e) {
                System.err.println("Client does not have SHA-256 hashing.");
                registrationFailure();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registrationSuccess(String nick) {
        try {
            App.getJungleClient().removeListener(handler);
            registrationSuccess = true;
            
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/homePage.fxml"));
            Parent root = loader.load();
            // Get the Controller from the FXMLLoader
            HomeController controller = loader.getController();
            // Set data in the controller
            controller.nick = nick;
            Scene scene = new Scene(root);
            App.setScene(scene);          
            controller.displayNickname();

        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Home page.");
        }
    }

    public void registrationFailure() {
        App.getJungleClient().removeListener(handler);
        flowPane.setDisable(false);
        regFailed.setVisible(true);
        alreadyRegistered.setVisible(true);
    }

    public void registrationFailure(String errorMessage) {
        regFailed.setText(errorMessage);
        System.err.println(errorMessage);
        registrationFailure();
    }

    boolean validateRegistrationInfo() {
        return validateEmailAddress() && validateNickname() && validatePassword() && validatePasswordConfirmation();
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

    boolean validateNickname() {
        String nickName = nickNameField.getText();
        boolean isNickNameValid = NICKNAME_PATTERN.matcher(nickName).matches();
        if (isNickNameValid) {
            nickNameField.setStyle("-fx-control-inner-background: " + COLOR_VALID);
        } else {
            nickNameField.setStyle("-fx-control-inner-background: " + COLOR_ERROR);
        }
        return isNickNameValid;
    }

    boolean validatePassword() {
        String password = passwordField.getText();
        boolean isPasswordValid = PASSWORD_PATTERN.matcher(password).matches();
        if (isPasswordValid) {
            passwordField.setStyle("-fx-control-inner-background: " + COLOR_VALID);
        } else {
            passwordField.setStyle("-fx-control-inner-background: " + COLOR_ERROR);
        }
        return isPasswordValid;
    }

    boolean validatePasswordConfirmation() {
        String password = passwordField.getText();
        String passwordConfirmed = passwordReenterField.getText();
        boolean isPasswordConfirmed = !passwordConfirmed.equals("") && password.equals(passwordConfirmed);
        if (isPasswordConfirmed) {
            passwordReenterField.setStyle("-fx-control-inner-background: " + COLOR_VALID);
        } else {
            passwordReenterField.setStyle("-fx-control-inner-background: " + COLOR_ERROR);
        }
        return isPasswordConfirmed;
    }

    public void initialize(URL location, ResourceBundle resources) {

        // TODO Auto-generated method stub

    }

    // FIXME: Is this method needed?
    public boolean getRegStatus() {
        return registrationSuccess;
    }

    // FIXME: Is this method needed?
    public void setRegStatus(boolean registrationSuccess) {
        this.registrationSuccess = registrationSuccess;

    }
}
