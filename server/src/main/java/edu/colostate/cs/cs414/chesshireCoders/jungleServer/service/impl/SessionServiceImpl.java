package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginAttemptDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserSessionDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.LoginAttempt;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.UserSession;

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
    public AuthToken authenticate(String email, String hashedPass, Connection connection) throws AccountNotFoundException, CredentialException, SQLException {

        Login login = fetchLoginInfo(email);

        if (login == null) throw new AccountNotFoundException("Error fetching account, has it been created?");
        if (!passwordOkay(hashedPass, login))
            failAuthentication(login);


        User user = fetchUserInfo(login.getUserID());
        assert user != null;

        JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        AuthToken token = Crypto.generateAuthToken();
        jungleConnection.authorize(user.getNickName(), token);

        saveLoginAttempt(true, user.getUserId());
        return token;
    }

    private void failAuthentication(Login login) throws SQLException, CredentialException {
        saveLoginAttempt(false, login.getUserID());
        if (getInvalidAttempts(login) > 3)
            lockAccount(login);
        throw new CredentialException("Bad password");
    }

    private void lockAccount(final Login login) throws SQLException {
        login.setIsLocked(true);
        manager.execute((DAOCommand<Void>) manager -> {
            LoginDAO loginDAO = manager.getLoginDAO();
            loginDAO.update(login);
            return null;
        });
    }

    private void saveLoginAttempt(final boolean success, final long userId) throws SQLException {
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
    public void expireSession(String token) throws SQLException {
        final UserSession userSession = new UserSession();
        userSession.setAuthToken(new AuthToken()
                .setToken(token)
                .setExpiration(System.currentTimeMillis()));

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

    @Override
    public boolean isAuthorized(Connection connection) throws InvalidConnectionException {
        return false;
    }

    private boolean passwordOkay(String givenPassword, Login login) {
        return login.getHashedPass().equals(givenPassword);
    }

    private Login fetchLoginInfo(final String email) {
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

    private User fetchUserInfo(final long userId) {
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

    private int getInvalidAttempts(Login login) throws SQLException {
        final Date checkSince = new Date(System.currentTimeMillis() - LOGIN_CHECK_PERIOD);
        return manager.execute(manager -> {
            LoginAttemptDAO loginAttemptDAO = manager.getLoginAttemptDAO();
            return loginAttemptDAO.getSuccessfulAttemptsSince(checkSince);
        });
    }

    public class InvalidConnectionException extends Exception {

        public InvalidConnectionException() {
            super();
        }

        public InvalidConnectionException(String message) {
            super(message);
        }
    }
}
