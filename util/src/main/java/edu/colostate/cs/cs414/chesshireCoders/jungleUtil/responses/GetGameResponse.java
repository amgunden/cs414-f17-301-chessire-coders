package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;


import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

public class GetGameResponse extends Response {

    private Game game;

    public GetGameResponse() {
        super();
    }

    public GetGameResponse(Game game) {
        this.game = game;
    }

    public GetGameResponse(int statusCode, String message) {
        super(statusCode, message);
    }

    public GetGameResponse(int statusCode, String errMsg, Game game) {
        super(statusCode, errMsg);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
