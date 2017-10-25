package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

public class UpdateSessionExpirationResponse extends Response {

    private long newExpirationTime;

    public UpdateSessionExpirationResponse() {

    }

    public UpdateSessionExpirationResponse(long newExpirationTime) {
        this.newExpirationTime = newExpirationTime;
    }

    public UpdateSessionExpirationResponse(int statusCode, String message) {
        super(statusCode, message);
    }

    public long getNewExpirationTime() {
        return newExpirationTime;
    }

    public void setNewExpirationTime(long newExpirationTime) {
        this.newExpirationTime = newExpirationTime;
    }
}
