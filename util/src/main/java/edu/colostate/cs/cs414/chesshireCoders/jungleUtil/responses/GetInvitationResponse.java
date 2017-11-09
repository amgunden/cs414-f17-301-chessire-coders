package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;

public class GetInvitationResponse extends Response {

    private Invitation invitation;

    public GetInvitationResponse() {
    }

    public GetInvitationResponse(Invitation invitation) {
        this.invitation = invitation;
    }

    public GetInvitationResponse(int statusCode, String errMsg, Invitation invitation) {
        super(statusCode, errMsg);
        this.invitation = invitation;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }
}
