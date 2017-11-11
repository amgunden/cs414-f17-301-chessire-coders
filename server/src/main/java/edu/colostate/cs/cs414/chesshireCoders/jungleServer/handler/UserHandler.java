package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.ConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserResponse;

import java.sql.SQLException;

public class UserHandler extends Listener {

    private ConnectionProvider provider = HikariConnectionProvider.getInstance();
    private DAOManager manager = DAOManager.instance(provider, PostgresDAOManager.class);

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof GetUserRequest) {
//            try {
//                connection.sendTCP(handleGetUser((GetUserRequest) received));
//            } catch (SQLException e) {
//                connection.sendTCP(new GetUserResponse(
//                        ResponseStatusCodes.SERVER_ERROR,
//                        e.getMessage()
//                ));
//            }
        }
    }

    private GetUserResponse handleGetUser(final GetUserRequest request) throws SQLException {
        return null;
//        User user = manager.execute(manager -> {
//            try {
//                UserDAO userDAO = manager.getUserDAO();
//                return userDAO.fin((long) request.getEmail());
//            } catch (SQLException e) {
//                e.printStackTrace();
//                return null;
//            }
//        });
//
//        if (user == null)
//            return new GetUserResponse(ResponseStatusCodes.CLIENT_ERROR, "Error retrieving user information");
//        else return new GetUserResponse(user);

//        java.sql.Connection connection = HikariConnectionProvider.getInstance().getConnection();
//        PostgresUserDAO postgresUserDAO = new PostgresUserDAO(connection);
//
//        try {
//            User user = postgresUserDAO.getUserByUserId(request.getUserID());
//            return new GetUserResponse(user);
//
//        } catch (SQLException e) {
//
//            return new GetUserResponse(SERVER_ERROR, e.getMessage());
//
//        } finally {
//            connection.close();
//        }
    }
}
