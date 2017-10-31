package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for pushing and pulling Game data to/from the database.
 */
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

    /**
     * Returns an instance of Game corresponding to the given ID from the database.
     * @param id The primary key of the game to retrieve.
     * @return Instance of game corresponding to the given ID
     * @throws SQLException
     */
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

    /**
     * Retrieves a list of games associated with a particular player ID number.
     * @param playerID
     * @return A list of games
     * @throws SQLException
     */
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

    /**
     * Inserts a new Game object into the database.
     * @param game
     * @throws SQLException
     */
    public void insert(Game game) throws SQLException {
        String insertStr = "INSERT INTO public.\"Game\""
                + "(\"gameStartDateTime\", \"gameEndDateTime\", \"PlayerOneID\", \"PlayerTwoID\", \"GameStatus\")"
                + "VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setTimestamp(1, game.getGameStart());
        statement.setTimestamp(2, game.getGameEnd());
        statement.setInt(3, game.getPlayerOneID());
        statement.setInt(4, game.getPlayerTwoID());
        statement.setString(5, game.getGameStatus().name().toLowerCase());

        statement.executeUpdate();
    }

    /**
     * Updates an existing game row in the database.
     * @param game The game to update, the gameID field must be set.
     * @throws SQLException
     */
    public void update(Game game) throws SQLException {
        String updateStr = "UPDATE public.\"Game\""
                        + "SET \"gameStartDateTime\" = ?,"
                        + "\"gameEndDateTime\" = ?,"
                        + "\"PlayerOneID\" = ?,"
                        + "\"PlayerTwoID\" = ?,"
                        + "\"GameStatus\" = ?"
                        + "WHERE \"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(updateStr);
        statement.setTimestamp(1, game.getGameStart());
        statement.setTimestamp(2, game.getGameEnd());
        statement.setInt(3, game.getPlayerOneID());
        statement.setInt(4, game.getPlayerTwoID());
        statement.setString(5, game.getGameStatus().name().toLowerCase());
        statement.setInt(6, game.getGameID());

        statement.executeUpdate();
    }

    /**
     * Deletes a row from the table, corresponding to the gameId field in 'game'
     * @param game the 'game' to delete (the id must be set)
     * @throws SQLException
     */
    public void delete(Game game) throws SQLException {
        String deleteStr = "DELETE FROM public.\"Game\" WHERE \"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setInt(1, game.getGameID());
        statement.executeUpdate();
    }
}
