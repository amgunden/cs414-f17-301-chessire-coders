package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class UpdateGameResponse extends Response {

    public UpdateGameResponse() {
    }

    public UpdateGameResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }
}
