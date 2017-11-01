package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;

import java.sql.SQLException;

public class RegistrationHandler extends AbstractRequestHandler {

    private JungleDB jungleDB = JungleDB.getInstance();

    public RegistrationHandler(JungleServer server) {
        super(server);
    }

    @Override
    public void addListeners() {
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<RegisterRequest>(RegisterRequest.class) {
                    @Override
                    public void run(Connection connection, RegisterRequest received) {
                        try {
                            connection.sendTCP(handleNewRegistration(received));
                        } catch (SQLException e) {
                            connection.sendTCP(new RegisterResponse(
                                    ResponseStatusCodes.SERVER_ERROR,
                                    e.getMessage()
                            ));
                        }
                    }
                }
        ));

        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<UnRegisterRequest>(UnRegisterRequest.class) {
                    @Override
                    public void run(Connection connection, UnRegisterRequest received) {
                        try {
                            connection.sendTCP(handleUnregisterRequest(received));
                        } catch (SQLException e) {
                            connection.sendTCP(new UnRegisterResponse(
                                    ResponseStatusCodes.SERVER_ERROR,
                                    e.getMessage()
                            ));
                        }
                    }
                }
        ));
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
