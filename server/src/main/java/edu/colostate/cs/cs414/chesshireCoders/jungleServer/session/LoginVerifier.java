package edu.colostate.cs.cs414.chesshireCoders.jungleServer.session;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginVerifier {


    private String email;
    private String password;

    private User user;
    private Login login;

    public LoginVerifier(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean authenticate() throws Exception {
        fetchLoginInfo();

        if (login == null) {
            throw new Exception("No matching user for given email address.");
        } else if (passwordMatches()) {
            fetchUserInfo();
            return true;
        } else {
            return false;
        }
    }

    public String getUserNickname() {
        return user.getNickName();
    }

    private boolean passwordMatches() {
        return login.getHashedPass().equals(this.password);
    }

    private void fetchUserInfo() throws SQLException {
        try (Connection connection = JungleDB.getInstance().getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            this.user = userDAO.getUserByUserId(login.getUserID());
        }
    }

    private void fetchLoginInfo() throws SQLException {
        try (Connection connection = JungleDB.getInstance().getConnection()) {
            LoginDAO loginDAO = new LoginDAO(connection);
            this.login = loginDAO.getLoginByEmail(this.email);
        }
    }
}
