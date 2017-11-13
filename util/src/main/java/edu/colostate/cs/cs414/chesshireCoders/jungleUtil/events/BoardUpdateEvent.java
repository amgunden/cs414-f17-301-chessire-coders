package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

public class BoardUpdateEvent {

    private long gameId;

    public BoardUpdateEvent() {
    }

    public BoardUpdateEvent(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public BoardUpdateEvent setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }
}
