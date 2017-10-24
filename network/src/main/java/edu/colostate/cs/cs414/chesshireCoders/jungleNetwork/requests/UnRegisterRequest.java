package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests;

public class UnRegisterRequest {
    String password;
    String email;
    String nickName;
    String nameFirst;
    String nameLast;

    public UnRegisterRequest(String password, String email) {
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }
}
