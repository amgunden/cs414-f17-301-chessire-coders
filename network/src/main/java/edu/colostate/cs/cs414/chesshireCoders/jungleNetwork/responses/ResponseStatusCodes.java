package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

/**
 * modeled after http status codes.
 */
public class ResponseStatusCodes {

    // Success codes are in the 200-299 range
    public static final int SUCCESS = 200;

    // Client errors are in the 400-499 range
    public static final int CLIENT_ERROR = 400;
    public static final int UNAUTHORIZED = 401;

    // Server error codes are in the 500-599 range
    public static final int SERVER_ERROR = 500;

}
