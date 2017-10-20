package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class UpdateSessionExpirationRequest {
    String accessToken;

    public UpdateSessionExpirationRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
