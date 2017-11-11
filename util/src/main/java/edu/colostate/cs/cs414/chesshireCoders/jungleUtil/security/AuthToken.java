package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import java.io.Serializable;

public class AuthToken implements Serializable {

    private String authenticationToken;
    private long expiresOn;

    public AuthToken() {
    }

    public AuthToken(String authenticationToken, long expiresOn) {
        setToken(authenticationToken);
        setExpiration(expiresOn);
    }

    public void load(String file) {
    }

    public void save(String file) {
    }

    public String getToken() {
        return authenticationToken;
    }

    public AuthToken setToken(String token) {
        this.authenticationToken = authenticationToken;
        return this;
    }

    public long getExpiration() {
        return expiresOn;
    }

    public AuthToken setExpiration(long expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }
}
