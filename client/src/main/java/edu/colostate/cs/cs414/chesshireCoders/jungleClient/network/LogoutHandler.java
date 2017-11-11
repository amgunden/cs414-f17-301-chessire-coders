package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LogoutRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LogoutResponse;
import javafx.application.Platform;

public class LogoutHandler extends Listener {

    private LogoutRequest request;

    public LogoutHandler() {}

    public void sendLogout(String accessToken) {
        request = new LogoutRequest(accessToken);
        App.getJungleClient().sendMessage(request);
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof LogoutResponse) {
            handleLogoutResponse((LogoutResponse) received);
        }
    }

    private void handleLogoutResponse(LogoutResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
                System.out.println("[INFO] User successfully logged out.");
            } else {
            	System.err.println("[ERROR] Server failed to log user out.");
            }
            App.getJungleClient().removeListener(this);
        });
    }
   
}
