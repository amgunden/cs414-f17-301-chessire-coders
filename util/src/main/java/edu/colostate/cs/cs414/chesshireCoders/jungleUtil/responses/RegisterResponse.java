package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class RegisterResponse extends Response {

    public RegisterResponse() {
    }

    public RegisterResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }
}
