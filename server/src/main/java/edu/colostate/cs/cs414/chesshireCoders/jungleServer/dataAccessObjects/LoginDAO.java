package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;

import java.sql.*;

/**
 * A class for pushing/pulling information about Login's from the database.
 */
public class LoginDAO extends AbstractDAO {


    public LoginDAO(Connection connection) {
        super(connection);
    }

    /**
     * Get the login info. associated with a userID.
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public Login getLoginByUserId(int userId) throws SQLException {
        String queryString = "SELECT *\n" +
                "FROM public.\"Login\"\n" +
                "WHERE \"Login\".\"UserID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, userId);
            statement.executeQuery();

            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    return readLogin(rs);
                } else return null;
            }
        }
    }

    /**
     * Gets the Login information associated with an email address from the database.
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public Login getLoginByEmail(String email) throws SQLException {
        String queryString = "SELECT *\n" +
                "FROM public.\"Login\"\n" +
                "WHERE \"Login\".\"Email\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setString(1, email);
            statement.executeQuery();

            try (ResultSet rs = statement.getResultSet()) {
                if (rs.next()) {
                    return readLogin(rs);
                } else return null;
            }
        }
    }

    /**
     * Add a new login object to the database
     *
     * @param login
     * @throws SQLException
     */
    public String insert(Login login) throws SQLException {
        String insertStr = "INSERT INTO public.\"Login\" (\"UserID\", \"Email\", \"HashedPass\") VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertStr, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, login.getUserID());
            statement.setString(2, login.getEmail());
            statement.setString(3, login.getHashedPass());

            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    return set.getString("Email");
                } else return null;
            }
        }
    }

    /**
     * Updates an existing login row in the database
     *
     * @param login
     * @throws SQLException
     */
    public String update(Login login) throws SQLException {
        String insertStr = "UPDATE public.\"Login\"\n" +
                "SET \"HashedPass\" = ?\n" +
                "WHERE \"Email\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(insertStr)) {
            statement.setString(1, login.getHashedPass());
            statement.setString(2, login.getEmail());

            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    return set.getString("Email");
                } else return null;
            }
        }
    }

    /**
     * Deletes an existing row from the database.
     *
     * @param login
     * @throws SQLException
     */
    public void delete(Login login) throws SQLException {
        String deleteStr = "DELETE FROM public.\"Login\"\n" +
                "WHERE \"Email\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteStr)) {
            statement.setString(1, login.getEmail());
            statement.executeUpdate();
        }
    }

    private Login readLogin(ResultSet rs) throws SQLException {
        Login login = new Login();
        login.setUserID(rs.getInt("UserID"));
        login.setEmail(rs.getString("Email"));
        login.setHashedPass(rs.getString("HashedPass"));
        return login;
    }
}
