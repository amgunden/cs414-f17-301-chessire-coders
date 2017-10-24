package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class GetUserGameHistoryRequest extends Session {
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private int userID;

    public GetUserGameHistoryRequest(String accessToken, int userID) {
        super(accessToken);
        this.userID = userID;
    }
}
