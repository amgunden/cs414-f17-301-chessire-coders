package edu.colostate.cs.cs414.chesshireCoders.jungleServer;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JungleConnection extends Connection {
	
	private static Logger logger = Logger.getLogger(JungleConnection.class.getSimpleName());

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
    
    @Override
    public int sendTCP(Object o) {
    	if (!(o instanceof FrameworkMessage.KeepAlive)) {
    		logger.log(Level.INFO, "Sending {0} to client with ID of {1}.", new Object[]{
    				o.getClass().getSimpleName(),
    					this.getID()});
    	}
    	return super.sendTCP(o);
    }
}
