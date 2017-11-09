package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class LogoutRequest extends Session {

    public LogoutRequest() {
        super();
    }

    public LogoutRequest(String accessToken) {
        super(accessToken);
    }
}
