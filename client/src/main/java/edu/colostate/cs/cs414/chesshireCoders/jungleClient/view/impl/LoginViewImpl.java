package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.ControllerFactory;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginViewImpl extends BaseView {

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

    private final AccountModel accountModel = AccountModel.getInstance();
    private final LoginController controller = ControllerFactory.getLoginController(this);

    public void initialize(URL location, ResourceBundle resources) {
        App.getWindow().setResizable(false);

        listenForLoginSuccess();
    }

    private void listenForLoginSuccess() {
        accountModel.loginSuccessProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue != null && newValue) {
                    loginSuccess();
                } else if (newValue != null) {
                    loginFailure();
                }
            }
        });
    }

    private void loginFailure() {
        Platform.runLater(() -> {
            btnLogin.setDisable(false);
        });
    }

    private void loginSuccess() {
        Platform.runLater(() -> {
            try {
                controller.dispose();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/homePage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                App.setScene(scene);
            } catch (IOException e) {
                System.err.println("ERROR: Unable to load fxml file for Home page.");
            }
        });
    }

    @FXML
    private void loginClicked() {
        System.out.println("btnLogin Clicked.");

        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + passwordField.getText());

        if (validateEmailAddress()) {
            try {
                btnLogin.setDisable(true);
                String email = emailField.getText();
                String password = Crypto.hashSHA256(passwordField.getText().getBytes());
                controller.sendLogin(email, password);
            } catch (Exception e) {
                showError(e.getMessage());
                if (loginErrorMsg.getText().length() == 0) loginErrorMsg.setText("Login failed.");
                btnLogin.setDisable(false);
            }
        }
    }

    @FXML
    public void registerClicked() {
        try {
            App.setScene("registerPage.fxml");
        } catch (IOException e) {
            System.err.println("ERROR: Unable to load fxml file for Register page.");
        }
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
