package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.sql.SQLException;

public interface SessionService {

    AuthToken authenticate(String email, String hashedPass, Connection connection) throws Exception;

    void expireSession(String token) throws SQLException;

    boolean isAccountLocked(String email);

    boolean isAuthorized(Connection connection) throws SessionServiceImpl.InvalidConnectionException, SQLException;

    boolean isExpired(Connection connection) throws SQLException;
}
