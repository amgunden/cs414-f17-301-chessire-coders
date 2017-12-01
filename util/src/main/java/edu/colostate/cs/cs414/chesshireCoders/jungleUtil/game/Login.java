package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

public class Login {

    private String hashedPass;
    private String email;
    private boolean isLocked;
    private String nickName;

    public Login() {
    }

    public Login(String hashedPass, String nickName, String email) {
        this.hashedPass = hashedPass;
        this.nickName = nickName;
        this.email = email;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public Login setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
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

    public String getNickName() {
        return nickName;
    }

    public Login setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }
}
