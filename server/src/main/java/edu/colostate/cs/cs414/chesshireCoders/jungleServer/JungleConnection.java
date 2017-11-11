package edu.colostate.cs.cs414.chesshireCoders.jungleServer;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class JungleConnection extends Connection {

    private AuthToken authToken;

    private String nickName; // The nickname of the user this connection is associated with.

    public JungleConnection() {
        super();
    }

    public boolean isAuthorized() {
        return (nickName != null)
                && (authToken != null);
    }

    public void authorize(String nickName, AuthToken authToken) {
        this.authToken = authToken;
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
