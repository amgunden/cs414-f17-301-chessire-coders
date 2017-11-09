package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

public class AuthToken {

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

    public void setToken(String token) {
        this.authenticationToken = authenticationToken;
    }

    public long getExpiration() {
        return expiresOn;
    }

    public void setExpiration(long expiresOn) {
        this.expiresOn = expiresOn;
    }
}
