package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.ErrorType;

public class ErrorResponse {
    private ErrorType errorType;
    private String errorMsg;

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ErrorResponse(ErrorType errorType, String errorMsg) {
        this.errorType = errorType;
        this.errorMsg = errorMsg;
    }
}
