package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

public class UpdateSessionExpirationResponse extends Response {

    public UpdateSessionExpirationResponse() {

    }

    public long getNewExpirationTime() {
        return newExpirationTime;
    }

    public void setNewExpirationTime(long newExpirationTime) {
        this.newExpirationTime = newExpirationTime;
    }

    private long newExpirationTime;

    public UpdateSessionExpirationResponse(int statusCode, String message) {
        super(statusCode, message);
    }
}
