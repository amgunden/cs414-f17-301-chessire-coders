package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class UpdateSessionExpirationRequest {
    private String accessToken;

    public UpdateSessionExpirationRequest() {
    }

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
