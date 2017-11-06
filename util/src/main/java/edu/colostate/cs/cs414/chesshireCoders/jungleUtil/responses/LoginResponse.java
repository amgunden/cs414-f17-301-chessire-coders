package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;

public class LoginResponse extends Response {

    private Login login;
    private String authToken;

    public LoginResponse(Login login, String authToken) {
        this.login = login;
        this.authToken = authToken;
    }

    public LoginResponse() {
    }

    public LoginResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public LoginResponse(int statusCode, String errMsg, Login login) {
        super(statusCode, errMsg);
        this.login = login;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    public String getAuthToken() {
    	return authToken;
    }
    
    public void setAuthToken(String token) {
    	authToken = token;
    }
}
