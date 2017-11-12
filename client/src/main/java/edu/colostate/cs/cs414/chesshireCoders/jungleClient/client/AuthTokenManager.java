package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.io.*;
import java.util.Date;

/**
 * The auth token manager is implemented as a singleton as the client should only have one authentication
 * token.
 * <p>
 * Allows saving and loading of authentication tokens to and from files.
 */
public class AuthTokenManager {

    private static AuthTokenManager ourInstance = new AuthTokenManager();

    public static AuthTokenManager getInstance() {
        return ourInstance;
    }

    private AuthToken authToken;
    private String email;

    private AuthTokenManager() {
    }

    private AuthTokenManager(AuthToken token) {
        setAuthToken(token);
    }

    public AuthToken getToken() {
    	if (authToken == null) return null;
    	
        return authToken;
    }
    
    public Date getExpiration() {
    	if (authToken == null) return null;
    	
        return authToken.getExpiration();
    }
    
    public void renewExpiration(Date newExpiration) {
    	if (authToken == null) return;
    	
        authToken.setExpiration(newExpiration);
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email=email;
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
