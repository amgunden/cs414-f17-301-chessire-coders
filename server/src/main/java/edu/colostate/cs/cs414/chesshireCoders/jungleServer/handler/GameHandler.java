package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.util.GameStateException;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.BoardUpdateEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UpdateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetGameResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UpdateGameResponse;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.*;

public class GameHandler extends Listener {

    private JungleServer server;
    private GameService gameService = new GameServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();

    public GameHandler() {

    }

    public GameHandler(JungleServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection connection, Object received) {
        try {
            if (received instanceof CreateGameRequest) {
                connection.sendTCP(handleCreateGame((CreateGameRequest) received, connection));
            } else if (received instanceof GetGameRequest) {
                connection.sendTCP(handleGetGame((GetGameRequest) received, connection));
            } else if (received instanceof UpdateGameRequest) {
                connection.sendTCP(handleUpdateGame((UpdateGameRequest) received, connection));
            }
        } catch (Exception e) {
            connection.sendTCP(new CreateGameResponse(SERVER_ERROR, e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * Update the stored game board
     */
    private UpdateGameResponse handleUpdateGame(UpdateGameRequest received, Connection connection) {
        JungleConnection jungleConnection = JungleConnection.class.cast(connection);
        try {
            if (sessionService.validateSessionRequest(received, connection)) {

                Game game = received.getGame();
                long sendingUserId = jungleConnection.getUserId();
                gameService.updateGame(sendingUserId, game);

                // notify opposing player
                long receivingUserId = (sendingUserId == received.getGame().getPlayerOneID()) ? received.getGame().getPlayerTwoID() : received.getGame().getPlayerOneID();
                server.sendToTCPWithUserId(new BoardUpdateEvent(game.getGameID()), receivingUserId);

                return new UpdateGameResponse(); // Defaults to success

            } else return new UpdateGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (GameStateException e) {
            return new UpdateGameResponse(CLIENT_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new UpdateGameResponse(SERVER_ERROR, e.getMessage());
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
            return new CreateGameResponse(SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * facilitates the retrieval of the requested game
     */
    private GetGameResponse handleGetGame(GetGameRequest received, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(received, connection)) {
                Game game = gameService.fetchGame(received.getGameID());
                return new GetGameResponse().setGame(game);
            } else return new GetGameResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new GetGameResponse(SERVER_ERROR, e.getMessage());
        }
    }
}
