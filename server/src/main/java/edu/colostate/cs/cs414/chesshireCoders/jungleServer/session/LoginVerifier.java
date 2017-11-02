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
    private Login login;
    private boolean isRegistered = false;

    public LoginVerifier(String email, String password) throws SQLException {
        this.email = email;
        this.password = password;
        getLoginInfo();
    }

    public boolean isEmailRegistered() {
        return login != null;
    }

    public boolean isPasswordOkay() {
        return login.getHashedPass().equals(password);
    }

    public User getUserInfo() throws SQLException {
        if (isEmailRegistered()) {
            Connection connection = JungleDB.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            try {
                return userDAO.getUserByUserId(login.getUserID());
            } finally {
                connection.close();
            }
        }
        return null;
    }

    private void getLoginInfo() throws SQLException {
        Connection connection = JungleDB.getInstance().getConnection();
        LoginDAO loginDAO = new LoginDAO(connection);
        try {
            login = loginDAO.getLoginByEmail(email);
        } finally {
            connection.close();
        }
    }
}
