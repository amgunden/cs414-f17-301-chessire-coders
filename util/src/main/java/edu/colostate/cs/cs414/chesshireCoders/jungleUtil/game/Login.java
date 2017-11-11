package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

public class Login {

    private String hashedPass;
    private long userID;
    private String email;
    private boolean isLocked;

    public Login() {
    }

    public Login(String hashedPass, int userID, String email) {
        this.hashedPass = hashedPass;
        this.userID = userID;
        this.email = email;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public Login setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
        return this;
    }

    public long getUserID() {
        return userID;
    }

    public Login setUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Login setEmail(String email) {
        this.email = email;
        return this;
    }

    public Login setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
