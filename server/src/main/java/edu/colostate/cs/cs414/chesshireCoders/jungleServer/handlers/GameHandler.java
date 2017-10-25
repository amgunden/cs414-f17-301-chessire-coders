package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.GetGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.GameDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;

import java.sql.SQLException;

import static edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.ResponseStatusCodes.SERVER_ERROR;
import static edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.ResponseStatusCodes.SUCCESS;

public class GameHandler extends AbstractRequestHandler {

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
                        connection.sendTCP(handleGetGame(received));
                    }
                }));
    }

    private Response handleGetGame(GetGameRequest request) {
        GameDAO gameDAO = new GameDAO();
        try {
            try {

                gameDAO.getConnection();
                Game game = gameDAO.getGameByID(request.getGameID());
                GetGameResponse response = new GetGameResponse();
                response.setGameID(game.getGameID());
                response.setPlayerOneID(game.getPlayerOneID());
                response.setPlayerTwoID(game.getPlayerTwoID());
                response.setPlayerTwoStatus(game.getPlayerTwoStatus());
                response.setStatus(game.getGameStatus());
                return response;

            } finally {
                gameDAO.closeConnection();
            }
        } catch (SQLException e) {
            return new GetGameResponse(SERVER_ERROR, e.getMessage());
        }
    }
}
