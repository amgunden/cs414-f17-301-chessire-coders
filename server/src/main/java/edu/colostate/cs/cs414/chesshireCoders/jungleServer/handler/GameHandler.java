package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;

public class GameHandler extends Listener {

    private HikariConnectionProvider hikariConnectionProvider = HikariConnectionProvider.getInstance();

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof CreateGameRequest) {
            connection.sendTCP(handleCreateGame((GetGameRequest) received));
        }
    }

    private CreateGameResponse handleCreateGame(GetGameRequest request) {

        return null;
    }
}
