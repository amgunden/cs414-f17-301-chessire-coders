package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

/**
 * modeled after http status codes.
 */
public class ResponseStatusCodes {

    // Information codes are in the 100 range
    public static final int INFORMATIONAL = 100;
    // nicely let clients know their invite response was rejected.
    public static final int GAME_ALREADY_STARTED = 101;

    // Success codes are in the 200-299 range
    public static final int SUCCESS = 200;

    // Client errors are in the 400-499 range
    public static final int CLIENT_ERROR = 400;
    public static final int UNAUTHORIZED = 401;
    // shout-out to HTTP response code #418, "I'm a teapot" - https://tools.ietf.org/html/rfc2324
    public static final int IM_A_TEAPOT = 418;

    // Server error codes are in the 500-599 range
    public static final int SERVER_ERROR = 500;

}
