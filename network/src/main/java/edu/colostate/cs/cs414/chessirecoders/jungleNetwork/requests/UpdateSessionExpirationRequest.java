package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.requests;

public class UpdateSessionExpirationRequest {
    String accessToken;

    public UpdateSessionExpirationRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
