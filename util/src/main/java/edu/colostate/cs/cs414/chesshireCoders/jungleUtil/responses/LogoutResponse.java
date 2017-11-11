package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class LogoutResponse extends Response {

    public LogoutResponse() {
    }

    public LogoutResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }
}
