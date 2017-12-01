package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

public class UserSession {

    private long sessionNumber;
    private String ipAddress;
    private AuthToken authToken;
    private String nickName;

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

    public String getNickName() {
        return nickName;
    }

    public UserSession setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSession that = (UserSession) o;

        if (getSessionNumber() != that.getSessionNumber()) return false;
        if (getIpAddress() != null ? !getIpAddress().equals(that.getIpAddress()) : that.getIpAddress() != null)
            return false;
        if (getAuthToken() != null ? !getAuthToken().equals(that.getAuthToken()) : that.getAuthToken() != null)
            return false;
        return getNickName() != null ? getNickName().equals(that.getNickName()) : that.getNickName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getSessionNumber() ^ (getSessionNumber() >>> 32));
        result = 31 * result + (getIpAddress() != null ? getIpAddress().hashCode() : 0);
        result = 31 * result + (getAuthToken() != null ? getAuthToken().hashCode() : 0);
        result = 31 * result + (getNickName() != null ? getNickName().hashCode() : 0);
        return result;
    }
}
