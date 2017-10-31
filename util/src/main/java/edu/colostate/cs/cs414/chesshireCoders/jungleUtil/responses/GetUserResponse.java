package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

public class GetUserResponse extends Response {

    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public GetUserResponse() {
    }

    public GetUserResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public GetUserResponse(int statusCode, String errMsg, User user) {
        super(statusCode, errMsg);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
