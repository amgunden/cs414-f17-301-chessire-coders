package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.GamesManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.SessionRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;
import javafx.application.Platform;

public class GetGameHandler extends Listener {

    private SessionRequest request;
    private Game game;
    
    public GetGameHandler() {
    }

    public void sendGetGame(long gameID) {
        request = new GetGameRequest(AuthTokenManager.getInstance().getToken(), gameID);
        App.getJungleClient().sendMessage(request);
        
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof GetGameResponse) {
            handleGetGameResponse((GetGameResponse) received);
        }
    }

    private void handleGetGameResponse(GetGameResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
            	this.game = response.getGame();
            	GamesManager.getInstance().createGame(game);
            } else {
                System.err.println("[ERROR]: Server was unable to get game.");
            }
            
        	App.getJungleClient().removeListener(this);

        });
    }
    
}
