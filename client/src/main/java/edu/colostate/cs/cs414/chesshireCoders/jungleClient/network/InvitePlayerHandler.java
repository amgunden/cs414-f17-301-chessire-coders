package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InvitePlayerResponse;
import javafx.application.Platform;

public class InvitePlayerHandler extends Listener {

    private InvitePlayerRequest request;

    public InvitePlayerHandler() {
    }

    public void sendInvitePlayer(String nickname, long gameID) {
        request = new InvitePlayerRequest(AuthTokenManager.getInstance().getToken(), nickname, gameID);
        App.getJungleClient().sendMessage(request);
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof InvitePlayerResponse) {
            handleInvitePlayerResponse((InvitePlayerResponse) received);
        }
    }

    private void handleInvitePlayerResponse(InvitePlayerResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
            	
            } else {
                System.err.println("[ERROR]: Server was unable to create game.");
            }
            
        	App.getJungleClient().removeListener(this);
        });
    }
   
}
