package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

public interface RegistrationService {

    void registerUser(String nickName, String email, String hashedPassword) throws Exception;

    void unregisterUser(String nickName) throws Exception;

    boolean isRegistered(String email) throws Exception;

    User fetchUserByNickName(String nickName) throws Exception;

    User fetchUserByEmail(String email) throws Exception;
}
