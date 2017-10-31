package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

public class Invitation {

    private int invitationId;
    private int senderId;
    private int recipientId;

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

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }
}
