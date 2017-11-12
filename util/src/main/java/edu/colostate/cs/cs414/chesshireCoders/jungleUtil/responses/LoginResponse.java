package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class LoginResponse extends Response {

    private AuthToken authToken;
    private String nickName;

    public LoginResponse() {
    }

    public LoginResponse(AuthToken authToken) {
        super();
        this.authToken = authToken;
    }

    public LoginResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public LoginResponse setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public LoginResponse setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
