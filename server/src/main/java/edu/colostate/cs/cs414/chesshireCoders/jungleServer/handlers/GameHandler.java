package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.GameDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes;

import java.sql.SQLException;

public class GameHandler extends AbstractRequestHandler {

    private JungleDB jungleDB = JungleDB.getInstance();

    public GameHandler(JungleServer server) {
        super(server);
    }

    @Override
    public void addListeners() {
        // GetGameRequest Handler
        server.addListener(new Listener.ThreadedListener(
                new FilteredListener<GetGameRequest>(GetGameRequest.class) {
                    @Override
                    public void run(Connection connection, GetGameRequest received) {
                        try {
                            connection.sendTCP(handleGetGame(received));
                        } catch (SQLException e) {
                            connection.sendTCP(new GetGameResponse(
                                    ResponseStatusCodes.SERVER_ERROR,
                                    e.getMessage()
                            ));
                        }
                    }
                }));
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
