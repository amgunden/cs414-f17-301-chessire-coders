package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;

import java.io.IOException;

public interface GameBoardController extends Controller {

    void sendQuitGame(long gameId) throws IOException;

    void sendInvitePlayerRequest(String nickname, long gameID) throws IOException;

    void sendUpdateGame(JungleGame jungleGame) throws IOException;
}
