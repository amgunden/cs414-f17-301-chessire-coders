package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import java.sql.SQLException;

public interface RegistrationService {

    void registerUser(String nickName, String email, String hashedPassword) throws SQLException;

    void unregisterUser(String email) throws SQLException;
}
