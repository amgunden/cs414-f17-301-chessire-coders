package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Login getLoginByUserId(int userId) throws SQLException {
        String queryString = "SELECT * FROM public.\"Login\""
                + "WHERE \"Login\".\"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, userId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        Login login = new Login();
        login.setUsername(rs.getString("Username"));
        login.setHashedPass(rs.getString("HashedPass"));
        login.setSalt(rs.getString("Salt"));
        login.setUserID(rs.getInt("UserID"));
        return login;
    }

    public Login getLoginByEmail(String email) throws SQLException {
        String queryString = "SELECT * FROM public.\"Login\""
                + "WHERE \"Login\".\"Username\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setString(1, email);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        Login login = new Login();
        login.setUsername(rs.getString("Username"));
        login.setHashedPass(rs.getString("HashedPass"));
        login.setSalt(rs.getString("Salt"));
        login.setUserID(rs.getInt("UserID"));
        return login;
    }

    public void insert(Login login) throws SQLException {
        String insertStr = "INSERT INTO public.\"Login\""
                + "(\"Username\", \"HashedPass\", \"Salt\",\"UserID\")"
                + "VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setString(1, login.getUsername());
        statement.setString(2, login.getHashedPass());
        statement.setString(3, login.getSalt());
        statement.setInt(4, login.getUserID());

        statement.executeUpdate();
    }

    public void update(Login login) throws SQLException {
        String insertStr = "UPDATE public.\"Login\" SET"
                + "\"HashedPass\" = ?,"
                + "\"Salt\" = ?"
                + "WHERE \"Username\" = ?";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setString(1, login.getHashedPass());
        statement.setString(2, login.getSalt());
        statement.setString(3, login.getUsername());

        statement.executeUpdate();
    }

    public void delete(Login login) throws SQLException {
        String deleteStr = "DELETE FROM public.\"Login\" WHERE \"Username\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setString(1, login.getUsername());
        statement.executeUpdate();
    }
}
