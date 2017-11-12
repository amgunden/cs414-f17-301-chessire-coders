package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;

public class InvitationEvent {
    private Invitation invite;

    public InvitationEvent() {
    }

    public int getInvitationID() {
        return invite.getInvitationId();
    }

    public void setInvitationID(int invitationID) {
        this.invite.setInvitationId(invitationID);
    }
    
    public Invitation getInvite()
    {
    	return invite;
    }

    public InvitationEvent(Invitation invite) {
        this.invite = invite;
    }
}
