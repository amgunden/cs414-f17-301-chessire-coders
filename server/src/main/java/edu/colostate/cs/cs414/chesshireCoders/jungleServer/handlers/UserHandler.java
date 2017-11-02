package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;

import java.sql.SQLException;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.SERVER_ERROR;

public class UserHandler extends Listener {

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof GetUserRequest) {
            try {
                connection.sendTCP(handleGetUser((GetUserRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new GetUserResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        }
    }

    private Response handleGetUser(GetUserRequest request) throws SQLException {

        java.sql.Connection connection = JungleDB.getInstance().getConnection();
        UserDAO userDAO = new UserDAO(connection);

        try {
            User user = userDAO.getUserByUserId(request.getUserID());
            return new GetUserResponse(user);

        } catch (SQLException e) {

            return new GetUserResponse(SERVER_ERROR, e.getMessage());

        } finally {
            connection.close();
        }
    }
}
