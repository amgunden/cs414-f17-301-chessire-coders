package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class LogoutHandler {

    public LogoutHandler() {}

    // The server doesn't respond. We can just exit.
    public void sendLogout(AuthToken accessToken) {
        LogoutRequest request = new LogoutRequest();
        request.setAuthToken(accessToken);
        App.getJungleClient().sendMessage(request);
    }
}
