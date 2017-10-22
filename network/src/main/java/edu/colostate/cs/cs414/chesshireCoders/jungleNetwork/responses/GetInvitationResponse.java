package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

public class GetInvitationResponse {
    int invitationID;
    int playerFromID;
    int playerToID;
    int gameID;

    public GetInvitationResponse(int invitationID, int playerFromID, int playerToID, int gameID) {
        this.invitationID = invitationID;
        this.playerToID = playerToID;
        this.playerFromID = playerFromID;
        this.gameID = gameID;
    }
}
