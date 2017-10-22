package edu.colostate.cs.cs414.chesshireCoders.jungleServer.data;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    private JungleDB jungleDB = null;
    private Connection connection = null;

    public GameDAO() {
        jungleDB = JungleDB.getInstance();
    }

    public void getConnection() throws SQLException {
        connection = jungleDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public Game getGameByID(int id) throws SQLException {
        String selectStr = "SELECT * FROM public.\"Game\" WHERE public.\"Game\".\"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(selectStr);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        rs.next();
        Game game = new Game();
        game.setGameID(rs.getInt("GameID"));
        game.setGameStart(rs.getTimestamp("gameStartDateTime"));
        game.setGameEnd(rs.getTimestamp("gameEndDateTime"));
        game.setPlayerOneID(rs.getInt("PlayerOneID"));
        game.setPlayerTwoID(rs.getInt("PlayerTwoID"));
        game.setGameStatus(GameStatus.valueOf(rs.getString("GameStatus").toUpperCase()));
        return game;
    }

    public List<Game> getGameByPlayerOneID(int playerID) throws SQLException {
        String selectStr = "SELECT * FROM public.\"Game\" WHERE public.\"Game\".\"PlayerOneID\" = ?";
        PreparedStatement statement = connection.prepareStatement(selectStr);
        statement.setInt(1, playerID);
        ResultSet rs = statement.executeQuery(selectStr);

        ArrayList<Game> games = new ArrayList<>();
        while (rs.next()) {
            Game game = new Game();
            game.setGameID(rs.getInt("GameID"));
            game.setGameStart(rs.getTimestamp("gameStartDateTime"));
            game.setGameEnd(rs.getTimestamp("gameEndDateTime"));
            game.setPlayerOneID(rs.getInt("PlayerOneID"));
            game.setPlayerTwoID(rs.getInt("PlayerTwoID"));
            game.setGameStatus(GameStatus.valueOf(rs.getString("GameStatus").toUpperCase()));
            games.add(game);
        }

        return games;
    }
}
