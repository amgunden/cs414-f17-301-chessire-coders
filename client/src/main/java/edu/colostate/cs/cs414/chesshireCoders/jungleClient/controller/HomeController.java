package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;

public interface HomeController extends Controller {

    void sendCreateGame();

    void fetchPlayerGames();

    void sendGetGame(long gameId);

    void sendAcceptInvite(Invitation invite);

    void sendRejectInvite(Invitation invite);

    void sendUnregister();

    void sendLogout();
}
