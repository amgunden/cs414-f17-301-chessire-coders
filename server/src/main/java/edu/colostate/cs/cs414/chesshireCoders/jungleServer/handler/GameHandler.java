package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.SERVER_ERROR;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.UNAUTHORIZED;

public class GameHandler extends Listener {

    private GameService gameService = new GameServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();

    @Override
    public void received(Connection connection, Object received) {
        try {
            if (received instanceof CreateGameRequest) {
                connection.sendTCP(handleCreateGame((CreateGameRequest) received, connection));
            } else if (received instanceof GetGameRequest) {
                connection.sendTCP(handleGetGame((GetGameRequest) received, connection));
            }
        } catch (Exception e) {
            connection.sendTCP(new CreateGameResponse(SERVER_ERROR, "An unknown error occurred on the server."));
            e.printStackTrace();
        }
    }

    /**
     * Facilitates the setup of a new game, and provides any error handling for performed operations.
     */
    private CreateGameResponse handleCreateGame(CreateGameRequest request, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(request, connection)) {
                JungleConnection jungleConnection = JungleConnection.class.cast(connection);
                long gameId = gameService.newGame(jungleConnection.getNickName()).getGameID();
                return new CreateGameResponse(gameId);
            } else return new CreateGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new CreateGameResponse(SERVER_ERROR, "An unknown error occurred");
        }
    }

    /**
     * facilitates the retrieval of the requested game
     */
    private GetGameResponse handleGetGame(GetGameRequest received, Connection connection) {
        return null;
    }
}
