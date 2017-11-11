package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import java.io.Serializable;
import java.util.Date;

public class LoginAttempt implements Serializable {
    private Date attemptTime;
    private boolean successful;
    private long userId;

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

    public long getUserId() {
        return userId;
    }

    public LoginAttempt setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
