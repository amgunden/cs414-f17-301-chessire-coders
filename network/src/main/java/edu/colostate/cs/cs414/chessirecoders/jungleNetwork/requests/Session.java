package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.requests;

public class Session {
    String accessToken;

    protected Session(String accessToken) {
        this.accessToken = accessToken;
    }
}
