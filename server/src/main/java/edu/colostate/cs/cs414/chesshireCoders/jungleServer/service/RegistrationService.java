package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.sql.SQLException;

public interface RegistrationService {

    void registerUser(String nickName, String email, String hashedPassword) throws Exception;

    void unregisterUser(String email) throws Exception;

    boolean isRegistered(String email) throws SQLException;

    User fetchUserByNickName(String nickName) throws SQLException;

    User fetchUserByEmail(String email) throws SQLException;
}
