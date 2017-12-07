package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;


import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

public class GetActiveGamesResponse extends Response {

    private List<Game> games;

    public GetActiveGamesResponse() {
        super();
    }

    public GetActiveGamesResponse(List<Game> games) {
        this.games = games;
    }

    public GetActiveGamesResponse(int statusCode, String message) {
        super(statusCode, message);
    }

    public List<Game> getGames() {
        return games;
    }

    public GetActiveGamesResponse setGames(List<Game> games) {
        this.games = games;
        return this;
    }
}
