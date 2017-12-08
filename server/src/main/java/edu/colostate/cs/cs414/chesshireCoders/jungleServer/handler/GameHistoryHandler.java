package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.SERVER_ERROR;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.UNAUTHORIZED;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserGameHistoryRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserGameHistoryResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

public class GameHistoryHandler extends Listener {

    private JungleServer server;
    private GameService gameService = new GameServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();

    public GameHistoryHandler(JungleServer server) {
        this.server = server;
    }

    public GameHistoryHandler() {

    }

    @Override
    public void received(Connection connection, Object received) {

        if (received instanceof GetUserGameHistoryRequest) {
            connection.sendTCP(handleGetUserGameHistory((GetUserGameHistoryRequest) received, connection));
        }
    }

    private GetUserGameHistoryResponse handleGetUserGameHistory(GetUserGameHistoryRequest request, Connection connection) {
    	try {
            if (sessionService.validateSessionRequest(request, connection)) {
            	
                List<Game> games = gameService.fetchUserGamesWithoutPieces(request.getNickName(), GameStatus.DRAW, GameStatus.WINNER_PLAYER_ONE, GameStatus.WINNER_PLAYER_TWO);
                return new GetUserGameHistoryResponse().setGames(games);
            } else return new GetUserGameHistoryResponse(UNAUTHORIZED, "You are not authorized to perform this action");
        } catch (Exception e) {
            e.printStackTrace();
            return new GetUserGameHistoryResponse(SERVER_ERROR, e.getMessage());
        }
    }
}
