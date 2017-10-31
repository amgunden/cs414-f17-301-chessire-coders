package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class GetInvitationResponse extends Response {

    private int invitationID;
    private int playerFromID;
    private int playerToID;
    private int gameID;

    public int getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(int invitationID) {
        this.invitationID = invitationID;
    }

    public int getPlayerFromID() {
        return playerFromID;
    }

    public void setPlayerFromID(int playerFromID) {
        this.playerFromID = playerFromID;
    }

    public int getPlayerToID() {
        return playerToID;
    }

    public void setPlayerToID(int playerToID) {
        this.playerToID = playerToID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public GetInvitationResponse(int invitationID, int playerFromID, int playerToID, int gameID) {
        this.invitationID = invitationID;
        this.playerToID = playerToID;
        this.playerFromID = playerFromID;
        this.gameID = gameID;
    }
}
