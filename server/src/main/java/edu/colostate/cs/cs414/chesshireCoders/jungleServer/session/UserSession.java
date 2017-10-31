package edu.colostate.cs.cs414.chesshireCoders.jungleServer.session;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

public class UserSession extends User {

    private String sessionToken;
    private long expiresOn;

    public UserSession(User user) {
        super(user);
        sessionToken = user.getNickName();
        expiresOn = -1;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public long getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(long expiresOn) {
        this.expiresOn = expiresOn;
    }
}
