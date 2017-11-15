package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class UpdateGameRequest extends SessionRequest {

    private Game game;

    public UpdateGameRequest() {
    }

    public UpdateGameRequest(AuthToken authToken, Game game) {
        super(authToken);
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
