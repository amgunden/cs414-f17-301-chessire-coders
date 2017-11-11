package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class LogoutRequest extends Session {

    public LogoutRequest() {
        super();
    }

    public LogoutRequest(AuthToken authToken) {
        super(authToken);
    }
}
