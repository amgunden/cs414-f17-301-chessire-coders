package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.*;

/**
 * A class for pushing/pulling information about Login's from the database.
 */
public class LoginDAO {
    private JungleDB jungleDB = null;
    private Connection connection = null;

    public LoginDAO() {
        jungleDB = JungleDB.getInstance();
    }

    public void getConnection() throws SQLException {
        connection = jungleDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Get the login info. associated with a userID.
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public Login getLoginByUserId(int userId) throws SQLException {
        String queryString = "SELECT * FROM public.\"Login\""
                + "WHERE \"Login\".\"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, userId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        if (!rs.isBeforeFirst()) {
            Login login = new Login();
            login.setUsername(rs.getString("Username"));
            login.setHashedPass(rs.getString("HashedPass"));
            login.setSalt(rs.getString("Salt"));
            login.setUserID(rs.getInt("UserID"));
            return login;
        } else return null;
    }

    /**
     * Gets the Login information associated with an email address from the database.
     * @param email
     * @return
     * @throws SQLException
     */
    public Login getLoginByEmail(String email) throws SQLException {
        String queryString = "SELECT * FROM public.\"Login\""
                + "WHERE \"Login\".\"Username\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setString(1, email);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        if (!rs.isBeforeFirst()) {
            Login login = new Login();
            login.setUsername(rs.getString("Username"));
            login.setHashedPass(rs.getString("HashedPass"));
            login.setSalt(rs.getString("Salt"));
            login.setUserID(rs.getInt("UserID"));
            return login;
        } else return null;
    }

    /**
     * Add a new login object to the database
     *
     * @param login
     * @throws SQLException
     */
    public String insert(Login login) throws SQLException {
        String insertStr = "INSERT INTO public.\"Login\""
                + "(\"Username\", \"HashedPass\", \"Salt\",\"UserID\")"
                + "VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, login.getUsername());
        statement.setString(2, login.getHashedPass());
        statement.setString(3, login.getSalt());
        statement.setInt(4, login.getUserID());

        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        if (set.next()) {
            return set.getString("Username");
        } else return null;
    }

    /**
     * Updates an existing login row in the database
     *
     * @param login
     * @throws SQLException
     */
    public String update(Login login) throws SQLException {
        String insertStr = "UPDATE public.\"Login\" SET"
                + "\"HashedPass\" = ?,"
                + "\"Salt\" = ?"
                + "WHERE \"Username\" = ?";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setString(1, login.getHashedPass());
        statement.setString(2, login.getSalt());
        statement.setString(3, login.getUsername());

        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        if (set.next()) {
            return set.getString("Username");
        } else return null;
    }

    /**
     * Deletes an existing row from the database.
     *
     * @param login
     * @throws SQLException
     */
    public void delete(Login login) throws SQLException {
        String deleteStr = "DELETE FROM public.\"Login\" WHERE \"Username\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setString(1, login.getUsername());
        statement.executeUpdate();
    }
}
