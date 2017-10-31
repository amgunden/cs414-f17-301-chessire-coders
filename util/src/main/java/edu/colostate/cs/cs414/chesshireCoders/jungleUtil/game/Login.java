package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

public class Login {

    private String username;
    private String hashedPass;
    private String salt;
    private int userID;

    public Login() {
    }

    public Login(String username, String hashedPass, String salt, int userID) {
        this.username = username;
        this.hashedPass = hashedPass;
        this.salt = salt;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
