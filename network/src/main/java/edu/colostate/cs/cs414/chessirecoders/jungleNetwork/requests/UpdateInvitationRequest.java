package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.requests;

import edu.colostate.cs.cs414.chessirecoders.jungleNetwork.types.Types;

public class UpdateInvitationRequest extends Session {
    int invitationID;
    Types.InvitationResponseType responseType;

    public UpdateInvitationRequest(String accessToken, int invitationID, Types.InvitationResponseType responseType) {
        super(accessToken);
        this.invitationID = invitationID;
        this.responseType = responseType;
    }
}
