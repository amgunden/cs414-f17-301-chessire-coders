package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class RegisterRequest {

    private String password;
    private String email;
    private String nickName;
    private String nameFirst;
    private String nameLast;

    public RegisterRequest() {

    }

    public RegisterRequest(String password, String email, String nickName, String nameFirst, String nameLast) {
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    public String getPassword() {
        return password;
    }

    public RegisterRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public RegisterRequest setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public RegisterRequest setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
        return this;
    }

    public String getNameLast() {
        return nameLast;
    }

    public RegisterRequest setNameLast(String nameLast) {
        this.nameLast = nameLast;
        return this;
    }
}
