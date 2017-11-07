package edu.colostate.cs.cs414.chesshireCoders.jungleServer.session;

import com.esotericsoftware.kryonet.Connection;

public class JungleConnection extends Connection {

    private String authToken;
    private long expirationTime;

    private String nickName; // The nickname of the user this connection is associated with.

    public JungleConnection() {
        super();
    }

    public boolean isAuthorized() {
        return (nickName != null)
                && (authToken != null)
                && (System.currentTimeMillis() < expirationTime);
    }

    public void authorize(String nickName, String authToken, long millisValid) {
        this.authToken = authToken;
        this.expirationTime = System.currentTimeMillis() + millisValid;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAuthToken() {
        return authToken;
    }
}
