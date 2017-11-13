package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;

import java.util.Date;

public class InvitationEvent {

    private Invitation invite;

    private Date invitationTime;

    public InvitationEvent() {
    }

    public InvitationEvent(Invitation invitation) {
        this.invite = invitation;
    }

    public Invitation getInvite() {
        return invite;
    }

    public InvitationEvent setInvite(Invitation invite) {
        this.invite = invite;
        return this;
    }

    public Date getInvitationTime() {
        return invitationTime;
    }

    public InvitationEvent setInvitationTime(Date invitationTime) {
        this.invitationTime = invitationTime;
        return this;
    }
}
