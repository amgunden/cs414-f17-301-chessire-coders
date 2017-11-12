package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.sql.SQLException;

public interface RegistrationService {

    void registerUser(String nickName, String email, String hashedPassword) throws SQLException;

    void unregisterUser(String email, AuthToken token) throws SQLException;
    
    User fetchUserByNickName(String nickName) throws SQLException;
    
    User fetchUserByEmail(String email) throws SQLException;
}
