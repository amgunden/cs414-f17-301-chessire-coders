package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetUserRequest extends Session {

    public GetUserRequest() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private int userID;

    public GetUserRequest(String accessToken, int userID) {
        super(accessToken);
        this.userID = userID;
    }
}
