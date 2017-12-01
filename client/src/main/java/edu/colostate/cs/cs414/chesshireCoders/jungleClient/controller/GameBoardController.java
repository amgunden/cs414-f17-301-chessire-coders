package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;

public interface GameBoardController extends Controller {

    void sendQuitGame(long gameId);

    void sendInvitePlayerRequest(String nickname, long gameID);

    void sendUpdateGame(JungleGame jungleGame);
}
