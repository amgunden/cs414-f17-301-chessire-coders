package edu.colostate.cs.cs414.chessireCoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chessireCoders.jungleNetwork.types.ErrorType;

public class ErrorResponse {
    ErrorType errorType;
    String errorMsg;

    public ErrorResponse(ErrorType errorType, String errorMsg) {
        this.errorType = errorType;
        this.errorMsg = errorMsg;
    }
}
