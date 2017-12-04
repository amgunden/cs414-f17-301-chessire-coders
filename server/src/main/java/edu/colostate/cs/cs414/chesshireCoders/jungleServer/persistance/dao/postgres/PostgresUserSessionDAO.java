package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserSessionDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.UserSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PostgresUserSessionDAO extends BaseDAO<UserSession, Long> implements UserSessionDAO {

    private static RowMapper<UserSession> USER_SESSION_ROW_MAPPER = rs -> {
        AuthToken token = new AuthToken()
                .setToken(rs.getString("auth_token"))
                .setExpiration(new Date(rs.getTimestamp("expires_on").getTime()));
        return new UserSession()
                .setIpAddress(rs.getString("ip_address"))
                .setNickName(rs.getString("nick_name"))
                .setSessionNumber(rs.getLong("session_id"))
                .setAuthToken(token);
    };

    PostgresUserSessionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public UserSession findByPrimaryKey(Long pk) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM user_session WHERE session_id = ?";
        return query(sql, USER_SESSION_ROW_MAPPER, pk).get(0);
    }

    @Override
    public List<UserSession> findAll() throws SQLException {
        //language=PostgreSQL
        return query("SELECT * FROM user_session", USER_SESSION_ROW_MAPPER);
    }

    @Override
    public Long create(UserSession userSession) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO user_session (\n" +
                "  ip_address,\n" +
                "  auth_token,\n" +
                "  expires_on,\n" +
                "  nick_name\n" +
                ") VALUES (?, ?, ?, ?)";
        return add(sql, Long.class,
                userSession.getIpAddress(),
                userSession.getAuthToken().getToken(),
                userSession.getAuthToken().getExpiration(),
                userSession.getNickName());
    }

    @Override
    public int update(UserSession userSession) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE user_session\n" +
                "SET auth_token = ?, expires_on = ?\n" +
                "WHERE nick_name = ?";
        return modify(sql,
                userSession.getAuthToken().getToken(),
                userSession.getAuthToken().getExpiration(),
                userSession.getNickName());
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param pk Primary key
     * @return rows affected (should only be 1)
     */
    @Override
    public int delete(Long pk) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM user_session WHERE session_id = ?";
        return modify(sql, pk);
    }

    @Override
    public int delete(UserSession userSession) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM user_session WHERE session_id = ?";
        return modify(sql, userSession.getSessionNumber());
    }

    @Override
    public int updateExpiration(UserSession session) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE user_session\n" +
                "SET expires_on = ?\n" +
                "WHERE auth_token = ?";
        return modify(sql,
                session.getAuthToken().getExpiration(),
                session.getAuthToken().getToken());
    }

    @Override
    public UserSession findByAuthToken(String token) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM user_session WHERE auth_token = ?";
        return query(sql, USER_SESSION_ROW_MAPPER, token).get(0);
    }
}
