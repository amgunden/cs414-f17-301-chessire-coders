package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * A class for pushing/pulling information about User's to/from the database.
 */
public class PostgresUserDAO extends BaseDAO<User, Long> implements UserDAO {

    private static RowMapper<User> USER_ROW_MAPPER = rs -> {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setNameLast(rs.getString("name_last"));
        user.setNameFirst(rs.getString("name_first"));
        user.setNickName(rs.getString("nick_name"));
        return user;
    };

    public PostgresUserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User findByPrimaryKey(Long pk) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM jungle_user WHERE jungle_user.user_id = ?";
        return query(sql, USER_ROW_MAPPER, pk).get(0);
    }

    @Override
    public List<User> findAll() throws SQLException {
        return query("SELECT * FROM jungle_user", USER_ROW_MAPPER);
    }

    @Override
    public User findByNickName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM jungle_user WHERE jungle_user.nick_name = ?";
        return query(sql, USER_ROW_MAPPER, nickName).get(0);
    }

    @Override
    public Long create(User user) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO jungle_user (name_first, name_last, nick_name) VALUES (?, ?, ?)";
        return add(sql, Long.class, user.getNameFirst(), user.getNameLast(), user.getNickName());
    }

    @Override
    public int update(User user) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE jungle_user\n" +
                "SET name_first = ?, name_last = ?, nick_name = ?\n" +
                "WHERE user_id = ?";
        return modify(sql, user.getNameFirst(), user.getNameLast(), user.getNickName(), user.getUserId());
    }

    public int delete(Long id) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM jungle_user WHERE user_id = ?";
        return modify(sql, id);
    }

    /**
     * Delete a row/rows from the database using an existing object for search criteria.
     * <p>
     * Implementations should build the DML statement to ignore NULL fields, and use only
     * non-NULL fields as criteria.
     *
     * @param user Search criteria object
     * @return rows affected.
     */
    @Override
    public int delete(User user) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM jungle_user WHERE nick_name = ?";
        return modify(sql, user.getNickName());
    }
}
