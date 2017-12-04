package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.SessionRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

public interface SessionService {

    AuthToken authenticate(String email, String hashedPass, Connection connection) throws Exception;

    void expireSession(String token) throws Exception;

    boolean isConnectionAuthorized(Connection connection) throws Exception;

    boolean isExpired(Connection connection) throws Exception;

    boolean validateSessionRequest(SessionRequest request, Connection connection) throws Exception;

    boolean isAccountLocked(String nickName) throws Exception;
}
