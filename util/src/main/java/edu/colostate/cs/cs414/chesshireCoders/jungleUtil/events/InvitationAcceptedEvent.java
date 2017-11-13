package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

public class InvitationAcceptedEvent {

    private long gameId;

    public InvitationAcceptedEvent() {
    }

    public InvitationAcceptedEvent(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
