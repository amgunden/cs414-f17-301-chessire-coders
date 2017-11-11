package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class GetUserRequest extends Session {

    public GetUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setUserID(String email) {
        this.email = email;
    }

    private String email;

    public GetUserRequest(String accessToken, String email) {
        super(accessToken);
        this.email = email;
    }
}
