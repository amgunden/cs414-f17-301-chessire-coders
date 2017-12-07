package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.GameBoardController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.InvitesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetAvailPlayersRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.QuitGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetAvailPlayersResponse;

import java.io.IOException;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameBoardControllerImpl extends BaseController implements GameBoardController {

    private JungleClient client = JungleClient.getInstance();
    private final Listener listener = new Listener.ThreadedListener(new GameBoardListener());
    
    private AccountModel accountModel = AccountModel.getInstance();
    private GamesModel gamesModel = GamesModel.getInstance();
    private InvitesModel invitesModel = InvitesModel.getInstance();


    public GameBoardControllerImpl(View view) {
        super(view);
        client.addListener(listener);
    }
    
    @Override
    public void dispose() {
        client.removeListener(listener);
    }

    @Override
    public void quitGame(long gameId) throws IOException {
        client.sendMessage(new QuitGameRequest(accountModel.getToken(), gameId));
    }

    @Override
    public void invitePlayer(String nickname, long gameID) throws IOException {
        client.sendMessage(new InvitePlayerRequest(accountModel.getToken(), nickname, gameID));
    }

    @Override
    public void updateGame(JungleGame jungleGame) throws IOException {
        gamesModel.updateOrAddGame(jungleGame);
        client.sendMessage(new UpdateGameRequest(accountModel.getToken(), new Game(jungleGame)));
    }
    
    public void getAvailPlayers( ) throws IOException {
    	client.sendMessage(new GetAvailPlayersRequest(accountModel.getToken(), accountModel.getNickName()));
    }
    
    /**
     * Update list of players available to receive invites
     */
    private void handleGetAvailPlayersResponse(GetAvailPlayersResponse response) {
        if (response.isSuccess()) {
            invitesModel.setAvailPlayers(response.getAvailUsers());
        }else view.showError(response.getErrMsg());
    }
    
    private class GameBoardListener extends Listener {
        @Override
        public void received(Connection connection, Object received) {
            try {
                // handle get avail players response
                if (received instanceof GetAvailPlayersResponse) {
                	handleGetAvailPlayersResponse((GetAvailPlayersResponse) received);
                }            	
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }
    }
}
