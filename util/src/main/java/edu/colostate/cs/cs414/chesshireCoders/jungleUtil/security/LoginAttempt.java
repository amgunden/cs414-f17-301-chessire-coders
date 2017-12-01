package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import java.io.Serializable;
import java.util.Date;

public class LoginAttempt implements Serializable {
    private Date attemptTime;
    private boolean successful;
    private long loginAttemptId;
    private String nickName;

    public LoginAttempt() {
    }

    public Date getAttemptTime() {
        return attemptTime;
    }

    public LoginAttempt setAttemptTime(Date attemptTime) {
        this.attemptTime = attemptTime;
        return this;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public LoginAttempt setSuccessful(boolean successful) {
        this.successful = successful;
        return this;
    }

    public long getLoginAttemptId() {
        return loginAttemptId;
    }

    public LoginAttempt setLoginAttemptId(long loginAttemptId) {
        this.loginAttemptId = loginAttemptId;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public LoginAttempt setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
