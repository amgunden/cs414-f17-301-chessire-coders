package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

public class Invitation {

    private long invitationId;
    private String senderNickname;
    private long gameId;
    private InvitationStatusType invitationStatus;
    private String recipientNickName;

    public Invitation() {
    }

    public long getInvitationId() {
        return invitationId;
    }

    public Invitation setInvitationId(long invitationId) {
        this.invitationId = invitationId;
        return this;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public Invitation setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
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

    public String getRecipientNickName() {
        return recipientNickName;
    }

    public Invitation setRecipientNickName(String recipientNickName) {
        this.recipientNickName = recipientNickName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invitation that = (Invitation) o;

        if (getInvitationId() != that.getInvitationId()) return false;
        if (getGameId() != that.getGameId()) return false;
        if (getSenderNickname() != null ? !getSenderNickname().equals(that.getSenderNickname()) : that.getSenderNickname() != null)
            return false;
        if (getInvitationStatus() != that.getInvitationStatus()) return false;
        return getRecipientNickName() != null ? getRecipientNickName().equals(that.getRecipientNickName()) : that.getRecipientNickName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getInvitationId() ^ (getInvitationId() >>> 32));
        result = 31 * result + (getSenderNickname() != null ? getSenderNickname().hashCode() : 0);
        result = 31 * result + (int) (getGameId() ^ (getGameId() >>> 32));
        result = 31 * result + (getInvitationStatus() != null ? getInvitationStatus().hashCode() : 0);
        result = 31 * result + (getRecipientNickName() != null ? getRecipientNickName().hashCode() : 0);
        return result;
    }
}
