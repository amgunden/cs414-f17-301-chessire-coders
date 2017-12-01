package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginAttemptDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserSessionDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.SessionRequest;
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

    /**
     * Given an email and password, authenticate the connection, and create a new session in the database.
     */
    @Override
    public AuthToken authenticate(String email, String hashedPass, Connection connection) throws Exception {

        Login login = fetchLoginInfo(email);

        if (login == null) throw new AccountNotFoundException("Error fetching account, has it been created?");
        if (!passwordOkay(hashedPass, login))
            failAuthentication(login);
        if (login.isLocked())
            throw new AccountLockedException("This account has been locked.");


        User user = fetchUserInfo(login.getNickName());
        if (user == null) throw new Exception("Error retrieving user information.");

        AuthToken token = Crypto.generateAuthToken();
        JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        createUserSession(
                user.getNickName(),
                token,
                jungleConnection.getRemoteAddressTCP().getAddress().toString()
        );
        jungleConnection.authorize(user.getNickName(), token);


        saveLoginAttempt(true, user.getNickName());
        return token;
    }

    /**
     * Creates and stores a new user session object.
     */
    private void createUserSession(final String nickName, final AuthToken token, final String ipAddress)
            throws Exception {
        final UserSession userSession = new UserSession()
                .setAuthToken(token)
                .setNickName(nickName)
                .setIpAddress(ipAddress);
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            manager.getUserSessionDAO().create(userSession);
            return null;
        });
    }

    /**
     * Creates failed login attempt row in the data base, and checks if the account should be locked
     */
    private void failAuthentication(Login login) throws Exception {
        saveLoginAttempt(false, login.getNickName());
        if (getInvalidAttempts(login.getNickName()) > 3)
            lockAccount(login);
        throw new CredentialException("Bad password");
    }

    /**
     * Locks the given account
     */
    private void lockAccount(final Login login) throws Exception {
        login.setIsLocked(true);
        manager.execute((DAOCommand<Void>) manager -> {
            LoginDAO loginDAO = manager.getLoginDAO();
            loginDAO.update(login);
            return null;
        });
    }

    /**
     * Creates a saves a login attempt object to the database.
     */
    private void saveLoginAttempt(final boolean success, final String nickName) throws Exception {
        final Date now = new Date(System.currentTimeMillis());
        manager.execute((DAOCommand<Void>) manager -> {
            LoginAttemptDAO loginAttemptDAO = manager.getLoginAttemptDAO();
            loginAttemptDAO.create(new LoginAttempt()
                    .setAttemptTime(now)
                    .setSuccessful(success)
                    .setNickName(nickName));
            return null;
        });
    }

    /**
     * expire a given session token
     */
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

    /**
     * Returns whether or not a given connection is valid and authenticated.
     */
    @Override
    public boolean isConnectionAuthorized(Connection connection) throws Exception {
        final JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        return jungleConnection.getAuthToken() != null
                && jungleConnection.getNickName() != null
                && doesConnectionMatchStored(jungleConnection);
    }

    /**
     * Checks if the token of a given connection is expired
     */
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

    /**
     * Checks that a given connection and request are valid and authenitcated, and whether or not the two tokens
     * match.
     */
    @Override
    public boolean validateSessionRequest(SessionRequest request, Connection connection) throws Exception {
        // check that the request is valid.
        final JungleConnection jungleConnection = JungleConnection.class.cast(connection);

        if (jungleConnection.getAuthToken() == null) return false;
        if (jungleConnection.getNickName() == null) return false;

        AuthToken requestToken = request.getAuthToken();
        AuthToken connectionToken = jungleConnection.getAuthToken();

        return doesConnectionMatchStored(jungleConnection) && requestToken.equals(connectionToken);
    }

    @Override
    public boolean isAccountLocked(String nickName) throws Exception {
        return manager.execute(manager -> manager.getLoginDAO().findByNickName(nickName).isLocked());
    }

    /**
     * Checks passwoard equality.
     */
    private boolean passwordOkay(String givenPassword, Login login) {
        return login.getHashedPass().equals(givenPassword);
    }

    /**
     * retireives login information from the database.
     */
    private Login fetchLoginInfo(final String email) throws Exception {
        try {
            return manager.execute(manager -> {
                LoginDAO loginDAO = manager.getLoginDAO();
                return loginDAO.findByPrimaryKey(email);
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * retrieves user information from the database
     */
    private User fetchUserInfo(final String nickName) throws Exception {
        try {
            return manager.execute(manager -> manager.getUserDAO().findByPrimaryKey(nickName));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the number of invalid login attempts within the last $LOGIN_CHECK_PERIOD minutes
     */
    private int getInvalidAttempts(String nickName) throws Exception {
        final Date checkSince = new Date(System.currentTimeMillis() - LOGIN_CHECK_PERIOD);
        return manager.execute(manager -> {
            LoginAttemptDAO loginAttemptDAO = manager.getLoginAttemptDAO();
            return loginAttemptDAO.getUnsuccessfulAttemptsSince(checkSince, nickName);
        });
    }

    /**
     * Checkst that a given connection token matches what is in the database.
     */
    private boolean doesConnectionMatchStored(JungleConnection jungleConnection) throws Exception {
        return manager.execute(manager -> {
            UserSession userSession = manager.getUserSessionDAO()
                    .findByAuthToken(jungleConnection.getAuthToken().getToken());

            boolean ipMatches = userSession.getIpAddress().equals(
                    jungleConnection.getRemoteAddressTCP().getAddress().toString()
            );
            boolean tokenMatches = userSession.getAuthToken().equals(jungleConnection.getAuthToken());
            return ipMatches && tokenMatches;
        });
    }
}
