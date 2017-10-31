package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

public class UnRegisterResponse extends Response {

    private boolean unregisterSuccess;
    private String msg;

    public UnRegisterResponse(boolean unregisterSuccess, String msg) {
        this.unregisterSuccess = unregisterSuccess;
        this.msg = msg;
    }

    public boolean isUnregisterSuccess() {
        return unregisterSuccess;
    }

    public void setUnregisterSuccess(boolean unregisterSuccess) {
        this.unregisterSuccess = unregisterSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
