package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;

public class LoginResponse extends Response {

    private Login login;

    public LoginResponse(Login login) {
        this.login = login;
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
}
