package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.ControllerFactory;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterViewImpl extends BaseView {

    private static final String COLOR_VALID = "#BAF5BA";
    private static final String COLOR_ERROR = "#F5C3BA";
    // matches valid email addresses
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    // Usernames must start with a letter, followed by any number of alpha-numeric characters
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Z]+[A-Z0-9]*$", Pattern.CASE_INSENSITIVE);
    // matches a string containing at least 6 alpha-numeric, or punctuation characters.
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[A-Z0-9\\p{Punct}]{6,}?$", Pattern.CASE_INSENSITIVE);

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

    private final AccountModel accountModel = AccountModel.getInstance();
    private final RegisterController controller = ControllerFactory.getRegisterController(this);

    private final ChangeListener<Boolean> loginSuccessListener = (observable, oldValue, newValue) -> {
        if (newValue != null && newValue) registrationSuccess();
        else if (newValue != null) registrationFailure();
    };

    @FXML
    public void initialize() {
        listenForRegisterSuccess();
    }

    private void listenForRegisterSuccess() {
        accountModel.loginSuccessProperty().addListener(loginSuccessListener);
    }

    @FXML
    private void loginClicked() {
        try {
            cleanup();
            App.setScene("loginPage.fxml");
        } catch (IOException e) {
            showError("ERROR: Unable to load fxml file for Login page.");
        }
    }

    private void cleanup() {
        accountModel.loginSuccessProperty().removeListener(loginSuccessListener);
        controller.dispose();
    }

    @FXML
    private void registerClicked() {

        System.out.println("btnRegistered Clicked.");

        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + passwordField.getText());

        boolean isRegistrationValid = validateRegistrationInfo();

        if (isRegistrationValid) {
            try {
                flowPane.setDisable(true);
                String email = emailField.getText();
                String nickName = nickNameField.getText();
                String password = Crypto.hashSHA256(passwordField.getText().getBytes());
                controller.sendRegistration(email, nickName, password);
            } catch (Exception e) {
                flowPane.setDisable(false);
                showError(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void registrationSuccess() {
        Platform.runLater(() -> {
            try {
                cleanup();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/homePage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                App.setScene(scene);
            } catch (IOException e) {
                showError("ERROR: Unable to load fxml file for Home page.");
            }
        });
    }

    private void registrationFailure() {
        Platform.runLater(() -> flowPane.setDisable(false));
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
}
