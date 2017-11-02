package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class for pushing/pulling information about User's to/from the database.
 */
public class UserDAO extends AbstractDAO {


    public UserDAO(Connection connection) {
        super(connection);
    }

    /**
     * Gets the User object associated with a given userID
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public User getUserByUserId(int userId) throws SQLException {
        String queryString = "SELECT *\n" +
                "FROM public.\"User\"\n" +
                "WHERE \"User\".\"UserID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, userId);
            statement.executeQuery();

            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    return readUser(rs);
                } else return null;
            }
        }
    }

    /**
     * Gets the user object associated with a given nickname.
     *
     * @param nickName
     * @return
     * @throws SQLException
     */
    public User getUserByNickName(String nickName) throws SQLException {
        String queryString = "SELECT *\n" +
                "FROM public.\"User\"\n" +
                "WHERE \"User\".\"NickName\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, nickName);
            statement.executeQuery();

            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    return readUser(rs);
                } else return null;
            }
        }
    }

    /**
     * Inserts a new User object into the database.
     *
     * @param user The user to insert
     * @throws SQLException
     */
    public int insert(User user) throws SQLException {
        String insertStr = "INSERT INTO public.\"User\" (\"NameFirst\", \"NameLast\", \"NickName\") VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertStr)) {
            statement.setString(1, user.getNameFirst());
            statement.setString(2, user.getNameLast()); // TODO: possibly provide handling for null value? (should be handled already)
            statement.setString(3, user.getNickName());

            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    return set.getInt("UserID");
                } else return -1;
            }
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The user to update (Must have ID set)
     * @throws SQLException
     */
    public int update(User user) throws SQLException {
        String insertStr = "UPDATE public.\"User\"\n" +
                "SET \"NameFirst\" = ?, \"NameLast\" = ?, \"NickName\" = ?\n" +
                "WHERE \"UserID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(insertStr)) {
            statement.setString(1, user.getNameFirst());
            statement.setString(2, user.getNameLast());
            statement.setString(3, user.getNickName());
            statement.setInt(4, user.getUserId());

            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    return set.getInt("UserID");
                } else return -1;
            }
        }
    }

    /**
     * Deletes an existing user from the database.
     *
     * @param user The user to delete (must have ID set)
     * @throws SQLException
     */
    public void delete(User user) throws SQLException {
        String deleteStr = "DELETE FROM public.\"User\"\n" +
                "WHERE \"UserID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteStr)) {
            statement.setInt(1, user.getUserId());
            statement.executeUpdate();
        }
    }

    private User readUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("UserID"));
        user.setNameFirst(rs.getString("NameFirst"));
        user.setNameLast(rs.getString("NameLast"));
        user.setNickName(rs.getString("NickName"));
        return user;
    }
}
