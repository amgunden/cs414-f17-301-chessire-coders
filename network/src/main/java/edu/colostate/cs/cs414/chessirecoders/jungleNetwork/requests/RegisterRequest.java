package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.requests;

public class RegisterRequest {
    String password;
    String email;
    String nickName;
    String nameFirst;
    String nameLast;

    public RegisterRequest(String password, String email, String nickName, String nameFirst, String nameLast) {
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }
}
