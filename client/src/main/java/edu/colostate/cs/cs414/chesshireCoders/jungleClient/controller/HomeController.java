package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;

import java.io.IOException;

public interface HomeController extends Controller {

    void sendCreateGame() throws IOException;

    void fetchPlayerGames();

    void sendGetGame(long gameId) throws IOException;

    void sendAcceptInvite(Invitation invite) throws IOException;

    void sendRejectInvite(Invitation invite) throws IOException;

    void sendUnregister() throws IOException;

    void sendLogout() throws IOException;
}
