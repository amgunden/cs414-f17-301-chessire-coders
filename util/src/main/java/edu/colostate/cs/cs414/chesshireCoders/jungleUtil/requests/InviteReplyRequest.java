package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public class InviteReplyRequest extends SessionRequest {

    private boolean inviteAccepted;
    private long invitationId;
    private String sendingNickName;
    private long gameId;

    public InviteReplyRequest() {
    }

    public InviteReplyRequest(AuthToken authToken) {
        super(authToken);
    }

    public InviteReplyRequest(boolean inviteAccepted, long invitationId) {
        this.inviteAccepted = inviteAccepted;
        this.invitationId = invitationId;
    }

    public InviteReplyRequest(AuthToken authToken, boolean inviteAccepted, long invitationId) {
        super(authToken);
        this.inviteAccepted = inviteAccepted;
        this.invitationId = invitationId;
    }

    public boolean isInviteAccepted() {
        return inviteAccepted;
    }

    public void setInviteAccepted(boolean inviteAccepted) {
        this.inviteAccepted = inviteAccepted;
    }

    public long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(long invitationId) {
        this.invitationId = invitationId;
    }

    @Override
    public InviteReplyRequest setAuthToken(AuthToken authToken) {
        super.setAuthToken(authToken);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InviteReplyRequest that = (InviteReplyRequest) o;

        if (isInviteAccepted() != that.isInviteAccepted()) return false;
        return getInvitationId() == that.getInvitationId();
    }

    @Override
    public int hashCode() {
        int result = (isInviteAccepted() ? 1 : 0);
        result = 31 * result + (int) (getInvitationId() ^ (getInvitationId() >>> 32));
        return result;
    }

    public String getSendingNickName() {
        return sendingNickName;
    }

    public void setSendingNickName(String sendingNickName) {
        this.sendingNickName = sendingNickName;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
