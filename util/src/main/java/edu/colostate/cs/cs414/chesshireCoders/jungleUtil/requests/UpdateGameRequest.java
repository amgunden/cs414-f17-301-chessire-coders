package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

public class UpdateGameRequest extends SessionRequest {

    private Game game;

    public UpdateGameRequest() {
    }

    public UpdateGameRequest(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public UpdateGameRequest setGame(Game game) {
        this.game = game;
        return this;
    }
}
