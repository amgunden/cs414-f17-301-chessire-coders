package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.LoginStatus;

public class LogoutResponse extends Response {

	private LoginStatus loginStatus; // TODO: remove, not needed.
    private Boolean logoutSuccess; // TODO: remove, not needed

    public LogoutResponse(int statusCode, String success) {
        super(statusCode, success);

        if (200 <= statusCode && statusCode <= 299)  logoutSuccess = true;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
    
    public Boolean getLogoutSuccess() {
    	return logoutSuccess;
    }

    public LogoutResponse(LoginStatus loginStatus, Boolean logoutSuccess) {
        this.loginStatus = loginStatus;
        this.logoutSuccess = logoutSuccess;
    }
}
