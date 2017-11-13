package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class SessionRequest {

    private AuthToken authToken;

    public SessionRequest() {
    }

    public SessionRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public SessionRequest setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
        return this;
    }
}
