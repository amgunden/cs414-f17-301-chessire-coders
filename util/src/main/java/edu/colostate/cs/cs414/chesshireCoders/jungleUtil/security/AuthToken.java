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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AuthToken authToken = (AuthToken) object;

        if (authenticationToken != null ? !authenticationToken.equals(authToken.authenticationToken) : authToken.authenticationToken != null)
            return false;
        return expiresOn != null ? expiresOn.equals(authToken.expiresOn) : authToken.expiresOn == null;
    }

    @Override
    public int hashCode() {
        int result = authenticationToken != null ? authenticationToken.hashCode() : 0;
        result = 31 * result + (expiresOn != null ? expiresOn.hashCode() : 0);
        return result;
    }
}
