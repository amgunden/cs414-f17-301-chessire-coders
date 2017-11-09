package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.daos.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.daos.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;

import java.sql.SQLException;

public class RegistrationHandler extends Listener {

    private JungleDB jungleDB = JungleDB.getInstance();

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof RegisterRequest) {
            try {
                connection.sendTCP(handleNewRegistration((RegisterRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new RegisterResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        } else if (received instanceof UnRegisterRequest) {
            try {
                connection.sendTCP(
                        handleUnregisterRequest((UnRegisterRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new UnRegisterResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        }
    }

    private RegisterResponse handleNewRegistration(RegisterRequest request) throws SQLException {

        java.sql.Connection connection = jungleDB.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        LoginDAO loginDAO = new LoginDAO(connection);

        try {
            connection.setAutoCommit(false);
            // populate user info
            User user = new User(
                    request.getNameFirst(),
                    request.getNameLast(),
                    request.getNickName()
            );

            // add user to db
            int userID = userDAO.insert(user);

            // Populate login info
            Login login = new Login(
                    request.getPassword(),
                    userID,
                    request.getEmail()
            );

            // Add login info to DB
            loginDAO.insert(login);
            connection.commit();

            return new RegisterResponse(ResponseStatusCodes.SUCCESS, "Registration successful");

        } catch (SQLException e) {

            connection.rollback();
            // TODO craft error based on error code.
            return new RegisterResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());

        } finally {

            connection.setAutoCommit(true);
            connection.close();
        }
    }

    private UnRegisterResponse handleUnregisterRequest(UnRegisterRequest request) throws SQLException {
        java.sql.Connection connection = jungleDB.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        LoginDAO loginDAO = new LoginDAO(connection);

        try {
            connection.setAutoCommit(false);
            Login login = loginDAO.getLoginByEmail(request.getEmail());
            User user = userDAO.getUserByUserId(login.getUserID());

            loginDAO.delete(login);
            userDAO.delete(user);
            connection.commit();

            return new UnRegisterResponse(ResponseStatusCodes.SUCCESS, "Un-registration successful");

        } catch (SQLException e) {

            connection.rollback();
            // TODO: Craft response based on error code.
            return new UnRegisterResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());

        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
