package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.LoginStatus;

public class LogoutResponse {

	private LoginStatus loginStatus;
    private String sessionToken;
    private Boolean logoutSuccess;
    

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
    
    public Boolean getLogoutSuccess() {
    	return logoutSuccess;
    }

    public LogoutResponse(LoginStatus loginStatus, String sessionToken, Boolean logoutSuccess) {
        this.loginStatus = loginStatus;
        this.sessionToken = sessionToken;
        this.logoutSuccess = logoutSuccess;
    }
}
