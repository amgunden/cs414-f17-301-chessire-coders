package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.GameDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgresGameDAO extends BaseDAO<Game, Long> implements GameDAO {

    private static RowMapper<Game> GAME_ROW_MAPPER = rs -> new Game()
            .setGameID(rs.getLong("game_id"))
            .setPlayerOneNickName(rs.getString("player_one_nick_name"))
            .setPlayerTwoNickName(rs.getString("player_two_nick_name"))
            .setGameStatus(GameStatus.valueOf(rs.getString("game_state")))
            .setGameStart(rs.getTimestamp("start_date_time"))
            .setGameEnd(rs.getTimestamp("end_date_time"))
            .setTurnOfPlayer(PlayerEnumType.valueOf(rs.getString("turn_of_player")));



    public PostgresGameDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Game> findByPlayerOneNickName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game WHERE player_one_nick_name = ?";
        return query(sql, GAME_ROW_MAPPER, nickName);
    }

    @Override
    public List<Game> findByPlayerTwoNickName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game WHERE player_two_nick_name = ?";
        return query(sql, GAME_ROW_MAPPER, nickName);
    }

    @Override
    public List<Game> findAllByNickName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game WHERE player_one_nick_name = ? OR player_two_nick_name = ?";
        return query(sql, GAME_ROW_MAPPER, nickName, nickName);
    }
    
    @Override
    public List<Game> findAllByNickName(String nickName, GameStatus... statuses) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM game WHERE (player_one_nick_name = ? OR player_two_nick_name = ?)";
        
        if (statuses.length > 0) { 
        	sql += " AND (game_state = ?";
	        for (int i = 1; i < statuses.length; i++) {
	        	sql += " OR game_state = ?";
			}
	        sql += " )";
        }
        return query(sql, GAME_ROW_MAPPER, nickName, nickName, statuses);
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
                "  player_one_nick_name,\n" +
                "  player_two_nick_name,\n" +
                "  game_state,\n" +
                "  start_date_time,\n" +
                "  end_date_time)\n" +
                "VALUES (?, ?, ?, ?, ?)";

        return add(sql, Long.class,
                game.getPlayerOneNickName(),
                game.getPlayerTwoNickName(),
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
        List<Game> games = query(sql, GAME_ROW_MAPPER, gameId);
        return games.isEmpty() ? null : games.get(0);
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
                "SET player_two_nick_name = ?, game_state = ?, start_date_time = ?, end_date_time = ?, turn_of_player = ?\n" +
                "WHERE game_id = ?";
        return modify(sql,
                game.getPlayerTwoNickName(),
                game.getGameStatus(),
                game.getGameStart(),
                game.getGameEnd(),
                game.getTurnOfPlayer().name(),
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
