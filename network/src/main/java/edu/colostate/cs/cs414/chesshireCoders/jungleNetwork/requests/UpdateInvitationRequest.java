package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.InvitationResponseType;

public class UpdateInvitationRequest extends Session {
    int invitationID;
    InvitationResponseType responseType;

    public UpdateInvitationRequest(String accessToken, int invitationID, InvitationResponseType responseType) {
        super(accessToken);
        this.invitationID = invitationID;
        this.responseType = responseType;
    }
}
