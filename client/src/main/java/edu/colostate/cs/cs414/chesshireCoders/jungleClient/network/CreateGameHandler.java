package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.GamesManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;
import javafx.application.Platform;

public class CreateGameHandler extends Listener {

    private CreateGameRequest request;

    public CreateGameHandler() {
    }

    public void sendCreateGame() {
        request = new CreateGameRequest(AuthTokenManager.getInstance().getToken());
        App.getJungleClient().sendMessage(request);
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof CreateGameResponse) {
            handleCreateGameResponse((CreateGameResponse) received);
        }
    }

    private void handleCreateGameResponse(CreateGameResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
            	GamesManager.getInstance().createGame(response.getGameId());
            } else {
                System.err.println("[ERROR]: Server was unable to create game.");
            }
            
        	App.getJungleClient().removeListener(this);
        });
    }
   
}
