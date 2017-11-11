package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class Session {

    private AuthToken authToken;

    public Session() {
    }

    public Session(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public Session setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
        return this;
    }
}
