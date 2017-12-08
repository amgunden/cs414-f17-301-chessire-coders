package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import java.io.IOException;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GameHistoryModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;

public interface HomeController extends Controller {

    void sendCreateGame() throws IOException;
    
    void sendGetActiveGames() throws IOException;

    void sendGetGame(long gameId) throws IOException;

    void sendGetUserGameHistory(String nickname, GameHistoryModel gameHistoryModel) throws IOException;
    
    void sendAcceptInvite(Invitation invite) throws IOException;

    void sendRejectInvite(Invitation invite) throws IOException;

    void sendUnregister() throws IOException;

    void sendLogout() throws IOException;
}
