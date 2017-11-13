package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginAttemptDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserSessionDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.LoginAttempt;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.UserSession;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import java.sql.SQLException;
import java.util.Date;

/**
 * The login manager uses the Singleton pattern so that there is only one,
 * easily accessible instance of LoginManager for all SessionHandler classes.
 * <p>
 * It tracks the set of all connections that have been authenticated and authorized
 * by a user.
 * <p>
 * This class uses Java's HashTables internally and synchronized ArrayLists, so it is thread-safe.
 */
public class SessionServiceImpl implements SessionService {

    private static final long LOGIN_CHECK_PERIOD = 10 * 60 * 1000; // 10 minutes

    private DAOManager manager = DAOManager.instance(
            HikariConnectionProvider.getInstance(),
            PostgresDAOManager.class);

    @Override
    public AuthToken authenticate(String email, String hashedPass, Connection connection) throws Exception {

        Login login = fetchLoginInfo(email);

        if (login == null) throw new AccountNotFoundException("Error fetching account, has it been created?");
        if (!passwordOkay(hashedPass, login))
            failAuthentication(login);
        if (isAccountLocked(login))
            throw new AccountLockedException("This account has been locked.");


        User user = fetchUserInfo(login.getUserID());
        assert user != null;

        AuthToken token = Crypto.generateAuthToken();
        JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        createUserSession(
                user.getUserId(),
                token,
                jungleConnection.getRemoteAddressTCP().toString()
        );
        jungleConnection.authorize(user.getNickName(), token);


        saveLoginAttempt(true, user.getUserId());
        return token;
    }

    private void createUserSession(final long userId, final AuthToken token, final String ipAddress)
            throws Exception {
        final UserSession userSession = new UserSession()
                .setAuthToken(token)
                .setUserId(userId)
                .setIpAddress(ipAddress);
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            manager.getUserSessionDAO().create(userSession);
            return null;
        });
    }

    private void failAuthentication(Login login) throws Exception {
        saveLoginAttempt(false, login.getUserID());
        if (getInvalidAttempts(login) > 3)
            lockAccount(login);
        throw new CredentialException("Bad password");
    }

    private void lockAccount(final Login login) throws Exception {
        login.setIsLocked(true);
        manager.execute((DAOCommand<Void>) manager -> {
            LoginDAO loginDAO = manager.getLoginDAO();
            loginDAO.update(login);
            return null;
        });
    }

    private void saveLoginAttempt(final boolean success, final long userId) throws Exception {
        final Date now = new Date(System.currentTimeMillis());
        manager.execute((DAOCommand<Void>) manager -> {
            LoginAttemptDAO loginAttemptDAO = manager.getLoginAttemptDAO();
            loginAttemptDAO.create(new LoginAttempt()
                    .setAttemptTime(now)
                    .setSuccessful(success)
                    .setUserId(userId));
            return null;
        });
    }

    @Override
    public void expireSession(String token) throws Exception {
        final UserSession userSession = new UserSession();
        userSession.setAuthToken(new AuthToken()
                .setToken(token)
                .setExpiration(new Date(System.currentTimeMillis())));

        manager.execute((DAOCommand<Void>) manager -> {
            UserSessionDAO userSessionDAO = manager.getUserSessionDAO();
            userSessionDAO.updateExpiration(userSession);
            return null;
        });
    }

    @Override
    public boolean isAccountLocked(String email) {
        return false;
    }

    public boolean isAccountLocked(Login login) {
        return login.isLocked();
    }

    @Override
    public boolean isAuthorized(Connection connection) throws Exception {
        final JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        if (jungleConnection.getAuthToken() == null) return false;
        return manager.execute(manager -> {
            UserSession userSession = manager.getUserSessionDAO()
                    .findByAuthToken(jungleConnection.getAuthToken().getToken());

            boolean ipMatches = userSession.getIpAddress().equals(jungleConnection.getRemoteAddressTCP().toString());
            boolean tokenMatches = userSession.getAuthToken().equals(jungleConnection.getAuthToken());
            return ipMatches && tokenMatches;
        });
    }

    @Override
    public boolean isExpired(Connection connection) throws Exception {
        final JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        final Date now = new Date(System.currentTimeMillis());
        return manager.execute(manager -> manager.getUserSessionDAO()
                .findByAuthToken(jungleConnection.getAuthToken().getToken())
                .getAuthToken()
                .getExpiration()
                .before(now));
    }

    private boolean passwordOkay(String givenPassword, Login login) {
        return login.getHashedPass().equals(givenPassword);
    }

    private Login fetchLoginInfo(final String email) throws Exception {
        try {
            return manager.execute(manager -> {
                LoginDAO loginDAO = manager.getLoginDAO();
                return loginDAO.findByEmail(email);
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User fetchUserInfo(final long userId) throws Exception {
        try {
            return manager.execute(manager -> {
                UserDAO userDAO = manager.getUserDAO();
                return userDAO.findByPrimaryKey(userId);
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getInvalidAttempts(Login login) throws Exception {
        final Date checkSince = new Date(System.currentTimeMillis() - LOGIN_CHECK_PERIOD);
        return manager.execute(manager -> {
            LoginAttemptDAO loginAttemptDAO = manager.getLoginAttemptDAO();
            return loginAttemptDAO.getUnsuccessfulAttemptsSince(checkSince);
        });
    }
}
