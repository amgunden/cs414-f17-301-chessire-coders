package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;


import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

public class GetGameResponse extends Response {

    private Game game;
    private PlayerEnumType viewingPlayer;

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

    public GetGameResponse setGame(Game game) {
        this.game = game;
        return this;
    }

	public PlayerEnumType getViewingPlayer() {
		return viewingPlayer;
	}

	public void setViewingPlayer(PlayerEnumType viewingPlayer) {
		this.viewingPlayer = viewingPlayer;
	}
}
