package edu.colostate.cs.cs414.chessireCoders.jungleNetwork.requests;

public class GetUserRequest extends Session {
    int userID;

    public GetUserRequest(String accessToken, int userID) {
        super(accessToken);
        this.userID = userID;
    }
}
