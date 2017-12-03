package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.GameBoardController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.QuitGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;

import java.io.IOException;

public class GameBoardControllerImpl extends BaseController implements GameBoardController {

    private JungleClient client = JungleClient.getInstance();
    private AccountModel accountModel = AccountModel.getInstance();
    private GamesModel gamesModel = GamesModel.getInstance();

    public GameBoardControllerImpl(View view) {
        super(view);
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
}
