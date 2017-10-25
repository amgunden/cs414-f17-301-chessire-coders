package edu.colostate.cs.cs414.chesshireCoders.jungleServer.session;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.User;

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
            UserDAO userDAO = new UserDAO();
            try {
                userDAO.getConnection();
                return userDAO.getUserByUserId(login.getUserID());
            } finally {
                userDAO.closeConnection();
            }
        }
        return null;
    }

    private void getLoginInfo() throws SQLException {
        LoginDAO loginDAO = new LoginDAO();
        try {
            loginDAO.getConnection();

            login = loginDAO.getLoginByEmail(email);


        } finally {
            loginDAO.closeConnection();
        }
    }
}
