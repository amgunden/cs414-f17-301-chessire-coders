package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for pushing/pulling information about User's to/from the database.
 */
public class UserDAO {
    private JungleDB jungleDB = null;
    private Connection connection = null;

    public UserDAO() {
        jungleDB = JungleDB.getInstance();
    }

    public void getConnection() throws SQLException {
        connection = jungleDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Gets the User object associated with a given userID
     * @param userId
     * @return
     * @throws SQLException
     */
    public User getUserByUserId(int userId) throws SQLException {
        String queryString = "SELECT * FROM public.\"User\""
                + "WHERE \"User\".\"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, userId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();

        if (!rs.isBeforeFirst()) {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setNameFirst(rs.getString("NameFirst"));
            user.setNameLast(rs.getString("NameLast"));
            user.setNickName(rs.getString("NickName"));
            return user;
        } else return null;
    }

    /**
     * Gets the user object associated with a given nickname.
     *
     * @param nickName
     * @return
     * @throws SQLException
     */
    public User getUserByNickName(String nickName) throws SQLException {
        String queryString = "SELECT * FROM public.\"User\""
                + "WHERE \"User\".\"NickName\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setString(1, nickName);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        if (!rs.isBeforeFirst()) {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setNameFirst(rs.getString("NameFirst"));
            user.setNameLast(rs.getString("NameLast"));
            user.setNickName(rs.getString("NickName"));
            return user;
        } else return null;
    }

    /**
     * Inserts a new User object into the database.
     *
     * @param user The user to insert
     * @throws SQLException
     */
    public int insert(User user) throws SQLException {
        String insertStr = "INSERT INTO public.\"User\""
                + "(\"NameFirst\", \"NameLast\",\"NickName\")"
                + "VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setString(1, user.getNameFirst());
        statement.setString(2, user.getNameLast()); // TODO: possibly provide handling for null value? (should be handled already)
        statement.setString(3, user.getNickName());

        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        if (set.next()) {
            return set.getInt("UserID");
        } else return -1;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The user to update (Must have ID set)
     * @throws SQLException
     */
    public int update(User user) throws SQLException {
        String insertStr = "UPDATE public.\"User\" SET"
                + "\"NameFirst\" = ?,"
                + "\"NameLast\" = ?,"
                + "\"NickName\" = ?"
                + "WHERE \"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setString(1, user.getNameFirst());
        statement.setString(2, user.getNameLast());
        statement.setString(3, user.getNickName());
        statement.setInt(4, user.getUserId());

        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        if (set.next()) {
            return set.getInt("UserID");
        } else return -1;
    }

    /**
     * Deletes an existing user from the database.
     *
     * @param user The user to delete (must have ID set)
     * @throws SQLException
     */
    public void delete(User user) throws SQLException {
        String deleteStr = "DELETE FROM public.\"User\" WHERE \"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setInt(1, user.getUserId());
        statement.executeUpdate();
    }

    private List<User> constructListFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setNameFirst(rs.getString("NameFirst"));
            user.setNameLast(rs.getString("NameLast"));
            user.setNickName(rs.getString("NickName"));
            users.add(user);
        }
        return users;
    }
}
