package edu.colostate.cs.cs414.chesshireCoders.jungleServer.session;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * The login manager uses the Singleton pattern so that there is only one,
 * easily accessible instance of LoginManager for all SessionHandler classes.
 * <p>
 * It tracks the set of all connections that have been authenticated and authorized
 * by a user.
 * <p>
 * This class uses Java's HashTables internally and synchronized ArrayLists, so it is thread-safe.
 */
public class LoginManager {

    private static LoginManager manager = new LoginManager();
    // maps an authentication token to a Connection
    private Hashtable<AuthToken, JungleConnection> tokenConnectionTable = new Hashtable<>();

    // maps a nick name to a number of Connections
    private Hashtable<String, List<JungleConnection>> nickNameConnectionTable = new Hashtable<>();

    // maps a connection ID to a Connection
    private Hashtable<Integer, JungleConnection> idConnectionTable = new Hashtable<>();

    private LoginManager() {
    }

    /**
     * @return Returns the instance of LoginManager.
     */
    public static LoginManager getManager() {
        return manager;
    }

    /**
     * Attempts to authenticate a user, and authorize they're associated connection.
     *
     * @param email      The email to authenticate with.
     * @param hashedPass The (hopefully) hashed password to authenticate with.
     * @param connection The connection to attempt to authorize for privileged actions.
     * @return Returns true on success, false on bad password.
     * @throws Exception An exception will be thrown on non-existent email address, or for an SQL error.
     */
    public boolean authenticate(String email, String hashedPass, Connection connection) throws Exception {

        LoginVerifier verifier = new LoginVerifier(email, hashedPass);
        boolean authenticationSuccess = verifier.authenticate();

        if (authenticationSuccess)
            authorizeConnection(verifier.getUserNickname(), connection);

        return authenticationSuccess;
    }

    /**
     * De-authorize a connection with a given ID.
     * Note that this will not terminate any TCP connections.*
     *
     * @param id The ID of the connection to de-authorize.
     */
    public void deauthorizeConnectionId(int id) {
        JungleConnection connection = idConnectionTable.remove(id);
        if (connection != null) {
            tokenConnectionTable.remove(connection.getAuthToken());
            nickNameConnectionTable.remove(connection.getNickName());
        }
    }

    /**
     * De-authorize a connection with a given authentication token.
     * Note that this will not terminate any TCP connections.
     *
     * @param token The token associated with a connection to de-authorize.
     */
    public void deauthorizeToken(AuthToken token) {
        JungleConnection connection = tokenConnectionTable.remove(token);
        if (connection != null) {
            idConnectionTable.remove(connection.getID());
            nickNameConnectionTable.remove(connection.getNickName());
        }
    }

    /**
     * De-authorizes ALL connections associated with a user's nickname.
     * Note that this will not terminate any TCP connections.
     *
     * @param nickName The nick name associated with any connections to be de-authorized.
     */
    public void deathourizeNickName(String nickName) {
        List<JungleConnection> connections = nickNameConnectionTable.remove(nickName);
        for (JungleConnection connection : connections) {
            if (connection != null) {
                idConnectionTable.remove(connection.getID());
                tokenConnectionTable.remove(connection.getAuthToken());
            }
        }
    }

    /**
     * Checks if a given connection object has been authenticated and authorized.
     *
     * @param connection The connection to check
     * @return True if authorized and authenticated, otherwise returns false.
     * @throws InvalidConnectionException If the connection object is not an instance of JungleConnection.
     */
    public boolean isConnectionAuthorized(Connection connection) throws InvalidConnectionException {
        return getJungleConnection(connection).isAuthorized();
    }

    /**
     * Fetches the list of connections associated with a user's nick name.
     *
     * @param nickName The user's nick name to find associated connections for.
     * @return A list of associated connections, or NULL if none were found.
     */
    public List<Connection> getUserConnections(String nickName) {
        return new ArrayList<>(nickNameConnectionTable.get(nickName));
    }

    /**
     * Fetches the connection associated with an authentication token.
     *
     * @param authToken The authentication token associated with a connection.
     * @return Returns a connection object if one was found, otherwise returns null.
     */
    public JungleConnection getConnectionFromAuthToken(AuthToken authToken) {
        return tokenConnectionTable.get(authToken);
    }

    /**
     * Fetches the connection associated with a connection ID.
     *
     * @param id The connection ID associated with a connection.
     * @return Returns a connection object if one was found, otherwise returns null.
     */
    public JungleConnection getConnectionFromId(int id) {
        return idConnectionTable.get(id);
    }

    private void authorizeConnection(String nickName, Connection connection) throws InvalidConnectionException {
        JungleConnection jungleConnection = getJungleConnection(connection);
        AuthToken authToken = Crypto.generateAuthToken();
        jungleConnection.authorize(nickName, authToken);

        tokenConnectionTable.put(authToken, jungleConnection);
        appendConnectionToNickName(nickName, getJungleConnection(connection));
        idConnectionTable.put(jungleConnection.getID(), jungleConnection);
    }

    private JungleConnection getJungleConnection(Connection connection) throws InvalidConnectionException {
        if (connection instanceof JungleConnection) {
            return (JungleConnection) connection;
        } else throw new InvalidConnectionException("Connection is not instance of JungleConnection.");
    }

    private void appendConnectionToNickName(String nickName, JungleConnection connection) {
        if (nickNameConnectionTable.containsKey(nickName)) {
            nickNameConnectionTable.get(nickName).add(connection);
        } else {
            List<JungleConnection> connections = Collections.synchronizedList(new ArrayList<JungleConnection>());
            nickNameConnectionTable.put(nickName, connections);
        }
    }

    public AuthToken getAuthToken(Connection connection) {
        return null;
    }

    /**
     *
     */
    public class InvalidConnectionException extends Exception {
        /**
         * Constructs a new exception with {@code null} as its detail message.
         * The cause is not initialized, and may subsequently be initialized by a
         * call to {@link #initCause}.
         */
        public InvalidConnectionException() {

        }

        /**
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         * @param message the detail message. The detail message is saved for
         *                later retrieval by the {@link #getMessage()} method.
         */
        public InvalidConnectionException(String message) {
            super(message);
        }
    }
}
