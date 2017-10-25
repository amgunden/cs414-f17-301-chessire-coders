package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.UnRegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;

import java.sql.SQLException;

public class RegistrationHandler extends AbstractRequestHandler {

    public RegistrationHandler(JungleServer server) {
        super(server);
    }

    @Override
    public void addListeners() {
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<RegisterRequest>(RegisterRequest.class) {
                    @Override
                    public void run(Connection connection, RegisterRequest received) {
                        connection.sendTCP(handleNewRegistration(received));
                    }
                }
        ));

        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<UnRegisterRequest>(UnRegisterRequest.class) {
                    @Override
                    public void run(Connection connection, UnRegisterRequest received) {
                        connection.sendTCP(handleUnregisterRequest(received));
                    }
                }
        ));
    }

    private RegisterResponse handleNewRegistration(RegisterRequest request) {
        UserDAO userDAO = new UserDAO();
        LoginDAO loginDAO = new LoginDAO();
        try {
            try {
                // populate user info
                User user = new User();
                user.setNameFirst(request.getNameFirst());
                user.setNameLast(request.getNameLast());
                user.setNickName(request.getNickName());

                // add user to db
                int userID = userDAO.insert(user);

                // Populate login info
                Login login = new Login();
                login.setUserID(userID);
                login.setSalt(""); // TODO: use salting
                login.setHashedPass(request.getPassword()); // TODO: hash password?
                login.setUsername(request.getEmail()); // TODO: refactor login.username to login.email

                // Add login info to DB
                loginDAO.insert(login);

                return new RegisterResponse(true, "Registration successful");

            } finally {
                userDAO.closeConnection();
                loginDAO.closeConnection();
            }
        } catch (SQLException e) {
            // TODO: Craft response based on error code.
            return new RegisterResponse(false, "Registration failed");
        }
    }

    private UnRegisterResponse handleUnregisterRequest(UnRegisterRequest request) {
        UserDAO userDAO = new UserDAO();
        LoginDAO loginDAO = new LoginDAO();
        try {
            try {
                userDAO.getConnection();
                loginDAO.getConnection();

                Login login = loginDAO.getLoginByEmail(request.getEmail());
                User user = userDAO.getUserByUserId(login.getUserID());

                loginDAO.delete(login);
                userDAO.delete(user);

                return new UnRegisterResponse(true, "Un-registration successful");
            } finally {
                userDAO.closeConnection();
                loginDAO.closeConnection();
            }
        } catch (SQLException e) {
            // TODO: Craft response based on error code.
            return new UnRegisterResponse(false, e.getMessage());
        }
    }
}
