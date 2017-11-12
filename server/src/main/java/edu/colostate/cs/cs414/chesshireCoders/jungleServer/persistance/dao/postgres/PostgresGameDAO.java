package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.GameDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgresGameDAO extends BaseDAO<Game, Long> implements GameDAO {

    private static RowMapper<Game> GAME_ROW_MAPPER = rs -> new Game()
            .setGameID(rs.getLong("game_id"))
            .setPlayerOneID(rs.getLong("player_one_id"))
            .setPlayerTwoID(rs.getLong("player_two_id"))
            .setGameStatus(GameStatus.valueOf(rs.getString("game_state")))
            .setGameStart(rs.getTimestamp("start_date_time"))
            .setGameEnd(rs.getTimestamp("end_date_time"));



    public PostgresGameDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Game> findByPlayerOneId(long userId) throws SQLException {
        String sql = "SELECT * FROM game WHERE player_one_id = ?";
        return query(sql, GAME_ROW_MAPPER, userId);
    }

    @Override
    public List<Game> findByPlayerTwoId(long userId) throws SQLException {
        String sql = "SELECT * FROM game WHERE player_two_id = ?";
        return query(sql, GAME_ROW_MAPPER, userId);
    }

    @Override
    public List<Game> findByUserId(long userId) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game WHERE player_one_id = ? OR player_two_id = ?";
        return query(sql, GAME_ROW_MAPPER, userId, userId);
    }

    /**
     * Add a new object to the table.
     *
     * @param game The object to add
     * @return The primary key of the added object.
     * @throws SQLException
     */
    @Override
    public Long create(Game game) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO game (\n" +
                "  player_one_id,\n" +
                "  player_two_id,\n" +
                "  game_state,\n" +
                "  start_date_time,\n" +
                "  end_date_time)\n" +
                "VALUES (?, ?, ?, ?, ?)";

        return add(sql, Long.class,
                game.getPlayerOneID(),
                game.getPlayerTwoID(),
                game.getGameStatus().name(),
                game.getGameStart(),
                game.getGameEnd()
        );
    }

    /**
     * Selects a row from the table using the primary key column.
     *
     * @param gameId primary key of row to select
     * @return Object corresponding to the selected row, or null on no result.
     * @throws SQLException
     */
    @Override
    public Game findByPrimaryKey(Long gameId) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game WHERE game_id = ?";
        return query(sql, GAME_ROW_MAPPER, gameId).get(0);
    }

    /**
     * Returns all rows in the table.
     *
     * @return A list of all rows (be careful!)
     * @throws SQLException
     */
    @Override
    public List<Game> findAll() throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game";
        return query(sql, GAME_ROW_MAPPER);
    }

    /**
     * Updates the row corresponding to the given object in the database.
     *
     * @param game object to update
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    @Override
    public int update(Game game) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE game\n" +
                "SET player_two_id = ?, game_state = ?, start_date_time = ?, end_date_time = ?\n" +
                "WHERE game_id = ?";
        return modify(sql,
                game.getPlayerTwoID(),
                game.getGameStatus(),
                game.getGameStart(),
                game.getGameEnd(),
                game.getGameID());
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param gameId Primary key
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    @Override
    public int delete(Long gameId) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM game WHERE game_id = ?";
        return modify(sql, gameId);
    }

    /**
     * Delete a row/rows from the database using an existing object for search criteria.
     * <p>
     * Implementations should build the DML statement to ignore NULL fields, and use only
     * non-NULL fields as criteria.
     *
     * @param game Search criteria object
     * @return rows affected.
     */
    @Override
    public int delete(Game game) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM game WHERE game_id = ?";
        return modify(sql, game.getGameID());
    }
}
