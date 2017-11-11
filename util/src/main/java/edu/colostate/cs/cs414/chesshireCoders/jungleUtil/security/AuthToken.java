package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import java.io.Serializable;
import java.util.Date;

public class AuthToken implements Serializable {

    private String authenticationToken;
    private Date expiresOn;

    public AuthToken() {
    }

    public AuthToken(String authenticationToken, Date expiresOn) {
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
        this.authenticationToken = token;
        return this;
    }

    public Date getExpiration() {
        return expiresOn;
    }

    public AuthToken setExpiration(Date expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }
}
