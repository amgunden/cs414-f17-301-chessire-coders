package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class UpdateSessionExpirationRequest {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UpdateSessionExpirationRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
