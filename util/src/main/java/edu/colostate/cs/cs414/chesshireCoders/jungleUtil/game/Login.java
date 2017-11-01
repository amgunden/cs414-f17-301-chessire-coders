package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

public class Login {

    private String hashedPass;
    private int userID;
    private String email;

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

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
