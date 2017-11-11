package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserSessionDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.UserSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgresUserSessionDAO extends BaseDAO<UserSession, Long> implements UserSessionDAO {

    private static RowMapper<UserSession> USER_SESSION_ROW_MAPPER = rs -> null;

    public PostgresUserSessionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public UserSession findByPrimaryKey(Long pk) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM public.\"user_session\" WHERE \"user_session\".\"session_id\" = ?";
        return query(sql, USER_SESSION_ROW_MAPPER, pk).get(0);
    }

    @Override
    public List<UserSession> findAll() throws SQLException {
        //language=PostgreSQL
        return query("SELECT * FROM public.\"user_session\"", USER_SESSION_ROW_MAPPER);
    }

    @Override
    public Long create(UserSession userSession) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO public.\"user_session\" (\n" +
                "  \"ip_address\",\n" +
                "  \"auth_token\",\n" +
                "  \"expires_on\",\n" +
                "  \"user_id\"\n" +
                ") VALUES (?, ?, ?, ?)";
        return add(sql, Long.class,
                userSession.getIpAddress(),
                userSession.getAuthToken().getToken(),
                userSession.getAuthToken().getExpiration(),
                userSession.getUserId());
    }

    @Override
    public int update(UserSession userSession) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE public.\"user_session\"\n" +
                "SET \"auth_token\" = ?, \"expires_on\" = ?\n" +
                "WHERE \"user_id\" = ?";
        return modify(sql,
                userSession.getAuthToken().getToken(),
                userSession.getAuthToken().getExpiration(),
                userSession.getUserId());
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param aLong Primary key
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    @Override
    public int delete(Long aLong) throws SQLException {
        return 0;
    }

    @Override
    public int delete(UserSession userSession) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int updateExpiration(UserSession session) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE public.\"user_session\"\n" +
                "SET \"expires_on\" = ?\n" +
                "WHERE \"auth_token\" = ?";
        return modify(sql,
                session.getAuthToken().getExpiration(),
                session.getAuthToken().getToken());
    }
}
