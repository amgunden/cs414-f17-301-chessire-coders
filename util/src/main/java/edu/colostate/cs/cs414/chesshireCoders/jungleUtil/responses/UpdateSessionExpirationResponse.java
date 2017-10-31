package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class UpdateSessionExpirationResponse extends Response {

    private long newExpirationTime;

    public UpdateSessionExpirationResponse() {
        super();
    }

    public UpdateSessionExpirationResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public UpdateSessionExpirationResponse(long newExpirationTime) {
        super();
        this.newExpirationTime = newExpirationTime;
    }

    public UpdateSessionExpirationResponse(int statusCode, String errMsg, long newExpirationTime) {
        super(statusCode, errMsg);
        this.newExpirationTime = newExpirationTime;
    }

    public long getNewExpirationTime() {
        return newExpirationTime;
    }

    public void setNewExpirationTime(long newExpirationTime) {
        this.newExpirationTime = newExpirationTime;
    }
}
