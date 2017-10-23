package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<User> getUserByUserId(int userId) throws SQLException {
        String queryString = "SELECT * FROM public.\"User\""
                + "WHERE \"User\".\"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, userId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public List<User> getUserByNickName(String nickName) throws SQLException {
        String queryString = "SELECT * FROM public.\"User\""
                + "WHERE \"User\".\"NickName\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setString(1, nickName);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public void insert(User user) throws SQLException {
        String insertStr = "INSERT INTO public.\"User\""
                + "(\"UserID\", \"NameFirst\", \"NameLast\",\"NickName\")"
                + "VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setInt(1, user.getUserId());
        statement.setString(2, user.getNameFirst());
        statement.setString(3, user.getNameLast());
        statement.setString(4, user.getNickName());

        statement.executeUpdate();
    }

    public void update(User user) throws SQLException {
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
    }

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
