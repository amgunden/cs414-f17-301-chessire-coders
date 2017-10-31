package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class Response {

    private int statusCode = ResponseStatusCodes.SUCCESS;
    private String errMsg;

    public Response() {
    }

    public Response(int statusCode, String errMsg) {

        this.statusCode = statusCode;
        this.errMsg = errMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
