package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

public interface RegistrationService {

    void registerUser(String nickName, String email, String hashedPassword) throws Exception;

    void unregisterUser(String email) throws Exception;

    boolean isRegistered(String email) throws Exception;
}
