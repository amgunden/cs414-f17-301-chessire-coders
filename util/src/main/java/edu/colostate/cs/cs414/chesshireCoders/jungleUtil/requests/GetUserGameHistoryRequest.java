package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetUserGameHistoryRequest extends Session {

    public GetUserGameHistoryRequest() {
    }

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
