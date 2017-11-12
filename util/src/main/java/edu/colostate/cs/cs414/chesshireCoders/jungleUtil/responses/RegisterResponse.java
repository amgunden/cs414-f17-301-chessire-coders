package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class RegisterResponse extends Response {

    private AuthToken authToken;
    private String nickName;

    public RegisterResponse() {
    }

    public RegisterResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public RegisterResponse setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public RegisterResponse setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
