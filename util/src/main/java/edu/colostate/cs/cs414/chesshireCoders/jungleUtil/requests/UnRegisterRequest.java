package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

public class UnRegisterRequest extends Session {

    private String email;

    public UnRegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public UnRegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }
}
