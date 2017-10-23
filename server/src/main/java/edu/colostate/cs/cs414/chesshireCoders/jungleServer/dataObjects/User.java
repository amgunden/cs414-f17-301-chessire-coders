package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects;

public class User {

    private int userId;
    private String nameFirst;
    private String nameLast;
    private String nickName;

    public User(int userId, String nameFirst, String nameLast, String nickName) {
        this.userId = userId;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.nickName = nickName;
    }

    public User(String nameFirst, String nameLast, String nickName) {
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.nickName = nickName;
    }

    public User(String nameFirst, String nickName) {
        this.nameFirst = nameFirst;
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
