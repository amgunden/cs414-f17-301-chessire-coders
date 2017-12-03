package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.*;

/**
 * The auth token manager is implemented as a singleton as the client should only have one authentication
 * token.
 * <p>
 * Allows saving and loading of authentication tokens to and from files.
 */
public class AccountModel {

    private static AccountModel ourInstance = new AccountModel();

    private ObjectProperty<Boolean> loginSuccess = new SimpleObjectProperty<>(null);

    private AuthToken authToken;
    private String nickName;
    private String email;

    private AccountModel() {
    }

    private AccountModel(AuthToken token) {
        setAuthToken(token);
    }

    public static AccountModel getInstance() {
        return ourInstance;
    }

    public AuthToken getToken() {
        return authToken;
    }

    public String getEmail() {
        return email;
    }

    public AccountModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public AccountModel setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public boolean isLoginSuccess() {
        return loginSuccess.getValue() != null && loginSuccess.getValue();
    }

    public AccountModel setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess.setValue(loginSuccess);
        return this;
    }

    public ObjectProperty<Boolean> loginSuccessProperty() {
        return loginSuccess;
    }

    public AccountModel setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
        return this;
    }

    /**
     * Save the saved authenticated token to a file.
     * <p>
     * This will allow authenticated sessions to persist through client restarts in the future.
     * (Assuming the token did not expire)
     *
     * @param file The file to save the token to.
     * @throws IOException on file write error.
     */
    public void save(String file) throws IOException {
        FileOutputStream fs = new FileOutputStream(new File(file));
        ObjectOutputStream oos = new ObjectOutputStream(fs);
        oos.writeObject(authToken);
    }

    /**
     * Load the saved authentication token from a file.
     * <p>
     * This will allow authenticated sessions to persist through client restarts in the future.
     * (Assuming the token did not expire)
     *
     * @param file The file to load the token from.
     * @throws IOException            on file read error.
     * @throws ClassNotFoundException if the given file does not contain an AuthToken object.
     */
    public void load(String file) throws IOException, ClassNotFoundException {
        FileInputStream is = new FileInputStream(new File(file));
        ObjectInputStream ois = new ObjectInputStream(is);
        this.authToken = (AuthToken) ois.readObject();
    }
}
