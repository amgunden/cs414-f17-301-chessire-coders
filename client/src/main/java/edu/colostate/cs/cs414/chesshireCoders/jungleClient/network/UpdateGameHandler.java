package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.GamesManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.SessionRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UpdateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;
import javafx.application.Platform;

public class UpdateGameHandler extends Listener {

    private SessionRequest request;

    public UpdateGameHandler() {
    }

    public void sendUpdateGame(Game game) {
        request = new UpdateGameRequest(AuthTokenManager.getInstance().getToken(), game);
        App.getJungleClient().sendMessage(request);
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof UpdateGameResponse) {
            handleUpdateGameResponse((UpdateGameResponse) received);
        }
    }

    private void handleUpdateGameResponse(UpdateGameResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
            } else {
                System.err.println("[ERROR]: Server was unable to update game.");
            }
            
        	App.getJungleClient().removeListener(this);
        });
    }
}
