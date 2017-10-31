package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

public class InvitationEvent {
    private int invitationID;

    public int getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(int invitationID) {
        this.invitationID = invitationID;
    }

    public InvitationEvent(int invitationID) {
        this.invitationID = invitationID;
    }
}
