package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class UnRegisterRequest extends SessionRequest {

    private String email;

    public UnRegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public UnRegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UnRegisterRequest setAuthToken(AuthToken authToken) {
        super.setAuthToken(authToken);
        return this;
    }
}
