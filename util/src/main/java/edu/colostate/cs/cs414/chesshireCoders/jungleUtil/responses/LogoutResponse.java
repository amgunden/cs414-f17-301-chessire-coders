package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.LoginStatus;

public class LogoutResponse extends Response {

    public LogoutResponse() {
    }

    public LogoutResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }
}
