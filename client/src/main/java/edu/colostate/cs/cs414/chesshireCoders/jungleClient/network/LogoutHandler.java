package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LogoutResponse;
import javafx.application.Platform;

public class LogoutHandler {

    public LogoutHandler() {}

    // The server doesn't respond. We can just exit.
    public void sendLogout(String accessToken) {
        LogoutRequest request = new LogoutRequest(accessToken);
        App.getJungleClient().sendMessage(request);
    }
}
