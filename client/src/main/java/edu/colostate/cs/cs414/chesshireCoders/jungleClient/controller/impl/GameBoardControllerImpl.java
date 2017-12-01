package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.GameBoardController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.GameBoardView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.QuitGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;

import java.io.IOException;

public class GameBoardControllerImpl extends BaseController<GameBoardView> implements GameBoardController {

    private JungleClient client = JungleClient.getInstance();
    private AccountModel accountModel = AccountModel.getInstance();

    public GameBoardControllerImpl(GameBoardView view) {
        super(view);
    }

    @Override
    public void sendQuitGame(long gameId) throws IOException {
        client.sendMessage(new QuitGameRequest(accountModel.getToken(), gameId));
    }

    @Override
    public void sendInvitePlayerRequest(String nickname, long gameID) throws IOException {
        client.sendMessage(new InvitePlayerRequest(accountModel.getToken(), nickname, gameID));
    }

    @Override
    public void sendUpdateGame(JungleGame jungleGame) throws IOException {
        client.sendMessage(new UpdateGameRequest(AccountModel.getInstance().getToken(), new Game(jungleGame)));
    }
}
