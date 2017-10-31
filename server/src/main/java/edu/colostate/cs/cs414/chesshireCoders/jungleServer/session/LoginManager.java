package edu.colostate.cs.cs414.chesshireCoders.jungleServer.session;

import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.util.Hashtable;

public class LoginManager {

    // Maps a Connection ID to a UserSession
    private Hashtable<Integer, UserSession> connectionIdSessionMap = new Hashtable<>();

    // Maps a userID to a connection
    private Hashtable<Integer, Connection> userIdConnectionMap = new Hashtable<>();

    // Maps a userID to a UserSession
    private Hashtable<Integer, UserSession> useIdSessionMap = new Hashtable<>();

    public boolean isLoggedIn(User user) {
        return userIdConnectionMap.containsKey(user.getUserId());
    }

    public boolean isLoggedIn(Connection connection) {
        return connectionIdSessionMap.containsKey(connection.getID());
    }

    public boolean isSessionExpired(Connection connection) {
        return connectionIdSessionMap.get(connection.getID()).getExpiresOn() > System.currentTimeMillis();
    }

    public boolean isSessionExpired(User user) {
        return useIdSessionMap.get(user.getUserId()).getExpiresOn() > System.currentTimeMillis();
    }

    public void loginUser(Connection connection, User user) {

        UserSession session = new UserSession(user);

        connectionIdSessionMap.put(connection.getID(), session);
        userIdConnectionMap.put(session.getUserId(), connection);
        useIdSessionMap.put(user.getUserId(), session);
    }


    public void logoutUser(User user) {
        Connection connection = userIdConnectionMap.remove(user.getUserId());
        connectionIdSessionMap.remove(connection.getID());
        useIdSessionMap.remove(user.getUserId());
    }

    public void logoutUser(Connection connection) {
        UserSession user = connectionIdSessionMap.remove(connection.getID());
        userIdConnectionMap.remove(user.getUserId());
        useIdSessionMap.remove(user.getUserId());
    }

    public Connection getUserConnection(User user) {
        return userIdConnectionMap.get(user.getUserId());
    }

    public User getConnectionUser(Connection connection) {
        return connectionIdSessionMap.get(connection.getID());
    }
}
