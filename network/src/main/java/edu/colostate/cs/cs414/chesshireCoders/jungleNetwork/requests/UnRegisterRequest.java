package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class UnRegisterRequest {
    private String password;
    private String email;
    private String nickName;
    private String nameFirst;
    private String nameLast;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UnRegisterRequest(String password, String email) {
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }
}
