package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game;

public class User {

    private long userId;
    private String nameFirst;
    private String nameLast;
    private String nickName;
    private boolean registered;

    public User() {
    }

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

    public User(User user) {
        this.nameFirst = user.nameFirst;
        this.nameLast = user.nameLast;
        this.nickName = user.nickName;
        this.userId = user.userId;
    }

    public long getUserId() {
        return userId;
    }

    public User setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public User setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
        return this;
    }

    public String getNameLast() {
        return nameLast;
    }

    public User setNameLast(String nameLast) {
        this.nameLast = nameLast;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public User setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public boolean isRegistered() {
        return registered;
    }

    public User setRegistered(boolean registered) {
        this.registered = registered;
        return this;
    }
}
