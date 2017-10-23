package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.LoginStatus;

public class LoginResponse {

    LoginStatus loginStatus;
    String sessionToken;
    long expiresOn;         // Expressed as milliseconds since Epoch

    public LoginResponse(String sessionToken, long expiresOn) {
        this.sessionToken = sessionToken;
        this.expiresOn = expiresOn;
    }
}
