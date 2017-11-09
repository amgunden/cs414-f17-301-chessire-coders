package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.daos.GameDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;

import java.sql.SQLException;

public class GameHandler extends Listener {

    private JungleDB jungleDB = JungleDB.getInstance();

    @Override
    public void received(Connection connection, Object received) {
        // GetGameRequest Handler
        if (received instanceof GetGameRequest) {
            try {
                connection.sendTCP(handleGetGame((GetGameRequest) received));
            } catch (SQLException e) {
                connection.sendTCP(new GetGameResponse(
                        ResponseStatusCodes.SERVER_ERROR,
                        e.getMessage()
                ));
            }
        }
    }

    private Response handleGetGame(GetGameRequest request) throws SQLException {
        java.sql.Connection connection = jungleDB.getConnection();
        GameDAO gameDAO = new GameDAO(connection);
        try {
            Game game = gameDAO.getGameByID(request.getGameID());
            return new GetGameResponse(game);
        } catch (SQLException e) {
            return new GetGameResponse(ResponseStatusCodes.SERVER_ERROR, e.getMessage());
        } finally {
            connection.close();
        }
    }
}
