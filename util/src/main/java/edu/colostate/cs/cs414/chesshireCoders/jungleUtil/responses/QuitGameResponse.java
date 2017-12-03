package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class QuitGameResponse extends Response {

    private long gameId;

    public QuitGameResponse() {
    }

    public QuitGameResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public long getGameId() {
        return gameId;
    }

    public QuitGameResponse setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }
}
