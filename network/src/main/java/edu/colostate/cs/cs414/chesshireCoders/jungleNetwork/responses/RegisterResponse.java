package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

public class RegisterResponse {
    private boolean registrationSuccess;
    private String msg; // Or RegistrationStatus status;

    public boolean isRegistrationSuccess() {
        return registrationSuccess;
    }

    public void setRegistrationSuccess(boolean registrationSuccess) {
        this.registrationSuccess = registrationSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RegisterResponse(boolean registrationSuccess, String msg) {
        this.registrationSuccess = registrationSuccess;
        this.msg = msg;
    }
}
