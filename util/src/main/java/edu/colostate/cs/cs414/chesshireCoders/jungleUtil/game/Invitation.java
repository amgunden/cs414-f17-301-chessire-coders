package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

public class Invitation {

    private long invitationId;
    private long senderId;
    private String senderNickname;
    private long recipientId;
    private long gameId;
    private InvitationStatusType invitationStatus;

    public Invitation() {
    }

    public Invitation(int invitationId, int senderId, int recipientId) {
        this.invitationId = invitationId;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public Invitation(int senderId, int recipientId) {
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public long getInvitationId() {
        return invitationId;
    }

    public Invitation setInvitationId(long invitationId) {
        this.invitationId = invitationId;
        return this;
    }

    public long getSenderId() {
        return senderId;
    }

    public Invitation setSenderId(long senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public Invitation setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
        return this;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public Invitation setRecipientId(long recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public long getGameId() {
        return gameId;
    }

    public Invitation setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    public InvitationStatusType getInvitationStatus() {
        return invitationStatus;
    }

    public Invitation setInvitationStatus(InvitationStatusType invitationStatus) {
        this.invitationStatus = invitationStatus;
        return this;
    }

    @Override
    public String toString() {
    	return senderNickname + " (" + invitationId + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invitation that = (Invitation) o;

        if (getInvitationId() != that.getInvitationId()) return false;
        if (getSenderId() != that.getSenderId()) return false;
        if (getRecipientId() != that.getRecipientId()) return false;
        if (getGameId() != that.getGameId()) return false;
        return getSenderNickname() != null ? getSenderNickname().equals(that.getSenderNickname()) : that.getSenderNickname() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getInvitationId() ^ (getInvitationId() >>> 32));
        result = 31 * result + (int) (getSenderId() ^ (getSenderId() >>> 32));
        result = 31 * result + (int) (getRecipientId() ^ (getRecipientId() >>> 32));
        result = 31 * result + (int) (getGameId() ^ (getGameId() >>> 32));
        result = 31 * result + (getSenderNickname() != null ? getSenderNickname().hashCode() : 0);
        return result;
    }
}
