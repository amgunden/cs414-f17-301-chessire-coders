package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import java.util.ArrayList;

public class RegisterResponse {
    boolean registrationSuccess;
    String msg; // Or RegistrationStatus status;
    public RegisterResponse(boolean registrationSuccess,  String msg) {
        this.registrationSuccess=registrationSuccess;
        this.msg=msg;
    }
    
    
    
}
