package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.util.Date;

public class UserSession {

    private long sessionNumber;
    private String ipAddress;
    private AuthToken authToken;
    private long userId;

    public UserSession() {
    }

    public long getSessionNumber() {
        return sessionNumber;
    }

    public UserSession setSessionNumber(long sessionNumber) {
        this.sessionNumber = sessionNumber;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public UserSession setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public UserSession setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public UserSession setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UserSession that = (UserSession) object;

        if (getSessionNumber() != that.getSessionNumber()) return false;
        if (getUserId() != that.getUserId()) return false;
        if (getIpAddress() != null ? !getIpAddress().equals(that.getIpAddress()) : that.getIpAddress() != null)
            return false;
        return getAuthToken() != null ? getAuthToken().equals(that.getAuthToken()) : that.getAuthToken() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getSessionNumber() ^ (getSessionNumber() >>> 32));
        result = 31 * result + (getIpAddress() != null ? getIpAddress().hashCode() : 0);
        result = 31 * result + (getAuthToken() != null ? getAuthToken().hashCode() : 0);
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        return result;
    }
}
