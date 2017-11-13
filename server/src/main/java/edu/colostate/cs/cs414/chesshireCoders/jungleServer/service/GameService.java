package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

import java.util.List;

public interface GameService {

    Game newGame(String playerOneNickName) throws Exception;

    Game startGame(long gameId, String playerTwoNickname) throws Exception;

    void updateGame(Game game) throws Exception;

    Game fetchGame(long gameId) throws Exception;

    List<Game> fetchUserGames(long userId) throws Exception;
}
