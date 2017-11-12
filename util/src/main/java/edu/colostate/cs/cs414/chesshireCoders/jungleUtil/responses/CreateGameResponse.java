package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class CreateGameResponse extends Response {

    private long gameID = -1;

    public CreateGameResponse() {
    }

    public CreateGameResponse(int statusCode, String errMsg, int gameID) {
        super(statusCode, errMsg);
        setGameID(gameID);
    }

    public CreateGameResponse(int statusCode, String msg) {
        super(statusCode, msg);
    }

    public CreateGameResponse(long gameId) {
        setGameID(gameId);
    }

    public long getGameID() {
        return gameID;
    }

    public CreateGameResponse setGameID(long gameID) {
        this.gameID = gameID;
        return this;
    }
}
