package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;

import java.sql.SQLException;

public class GameHandler extends Listener {

    private HikariConnectionProvider hikariConnectionProvider = HikariConnectionProvider.getInstance();

    @Override
    public void received(Connection connection, Object received) {

    }

    private Response handleCreateGame(GetGameRequest request) throws SQLException {

        return null;
    }
}
