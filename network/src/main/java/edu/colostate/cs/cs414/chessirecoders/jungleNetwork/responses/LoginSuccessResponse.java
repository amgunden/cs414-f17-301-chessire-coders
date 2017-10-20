package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.responses;

public class LoginSuccessResponse {
    String sessionToken;
    long expiresOn;         // Expressed as milliseconds since Epoch

    public LoginSuccessResponse(String sessionToken, long expiresOn) {
        this.sessionToken = sessionToken;
        this.expiresOn = expiresOn;
    }
}
