package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationResponseType;

public class UpdateInvitationRequest extends Session {
    private int invitationID;

    public InvitationResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(InvitationResponseType responseType) {
        this.responseType = responseType;
    }

    private InvitationResponseType responseType;

    public UpdateInvitationRequest(String accessToken, int invitationID, InvitationResponseType responseType) {
        super(accessToken);
        this.invitationID = invitationID;
        this.responseType = responseType;
    }
}
