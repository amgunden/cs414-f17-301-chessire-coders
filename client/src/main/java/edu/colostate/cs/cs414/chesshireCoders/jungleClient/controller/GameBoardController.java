package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;

import java.io.IOException;
import java.util.List;

public interface GameBoardController extends Controller {

    void quitGame(long gameId) throws IOException;

    void invitePlayer(String nickname, long gameID) throws IOException;

    void updateGame(JungleGame jungleGame) throws IOException;
    
    void getAvailPlayers( ) throws IOException;
    
}
