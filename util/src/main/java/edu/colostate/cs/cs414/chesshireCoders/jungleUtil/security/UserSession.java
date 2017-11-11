package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.util.Date;

public class UserSession {

    private long sessionNumber;
    private String ipAddress;
    private AuthToken authToken;
    private Date expiresOn;
    private User user;

    public UserSession() {
    }

    public long getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(long sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UserSession that = (UserSession) object;

        if (getSessionNumber() != that.getSessionNumber()) return false;
        if (getIpAddress() != null ? !getIpAddress().equals(that.getIpAddress()) : that.getIpAddress() != null)
            return false;
        if (getAuthToken() != null ? !getAuthToken().equals(that.getAuthToken()) : that.getAuthToken() != null)
            return false;
        if (getExpiresOn() != null ? !getExpiresOn().equals(that.getExpiresOn()) : that.getExpiresOn() != null)
            return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getSessionNumber() ^ (getSessionNumber() >>> 32));
        result = 31 * result + (getIpAddress() != null ? getIpAddress().hashCode() : 0);
        result = 31 * result + (getAuthToken() != null ? getAuthToken().hashCode() : 0);
        result = 31 * result + (getExpiresOn() != null ? getExpiresOn().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
