package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class Session {
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;

    protected Session(String accessToken) {
        this.accessToken = accessToken;
    }
}
