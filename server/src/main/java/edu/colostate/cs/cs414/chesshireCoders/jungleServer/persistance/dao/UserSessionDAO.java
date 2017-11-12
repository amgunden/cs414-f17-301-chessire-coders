package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.UserSession;

import java.sql.SQLException;

public interface UserSessionDAO extends GenericDAO<UserSession, Long> {

    int updateExpiration(UserSession session) throws SQLException;

    UserSession findByAuthToken(String token) throws SQLException;
}
