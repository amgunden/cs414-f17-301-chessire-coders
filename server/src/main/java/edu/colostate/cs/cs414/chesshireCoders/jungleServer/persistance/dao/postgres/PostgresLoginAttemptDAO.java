package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginAttemptDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.LoginAttempt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PostgresLoginAttemptDAO extends BaseDAO<LoginAttempt, Long>
        implements LoginAttemptDAO {

    private static RowMapper<LoginAttempt> LOGIN_ATTEMPT_ROW_MAPPER = rs -> null;

    PostgresLoginAttemptDAO(Connection connection) {
        super(connection);
    }

    @Override
    public LoginAttempt findByPrimaryKey(Long pk) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM login_attempt WHERE login_attempt_id = ?";
        return query(sql, LOGIN_ATTEMPT_ROW_MAPPER, pk).get(0);
    }

    @Override
    public List<LoginAttempt> findAll() throws SQLException {
        //language=PostgreSQL
        return query("SELECT * FROM login_attempt", LOGIN_ATTEMPT_ROW_MAPPER);
    }

    @Override
    public Long create(LoginAttempt loginAttempt) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO login_attempt (login_attempt_time, login_successful, nick_name) VALUES (?, ?, ?)";
        return add(sql, Long.class, loginAttempt.getAttemptTime(), loginAttempt.isSuccessful(), loginAttempt.getNickName());
    }

    @Override
    public int update(LoginAttempt loginAttempt) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE login_attempt\n" +
                "SET login_attempt_time = ?, login_successful = ?\n" +
                "WHERE nick_name = ?";
        return modify(sql, loginAttempt.getAttemptTime(), loginAttempt.isSuccessful(), loginAttempt.getNickName());
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param loginAttemptId Primary key
     * @return rows affected (should only be 1)
     */
    @Override
    public int delete(Long loginAttemptId) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM login_attempt WHERE login_attempt_id = ?";
        return modify(sql, loginAttemptId);
    }

    @Override
    public int delete(LoginAttempt loginAttempt) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM login_attempt WHERE login_attempt_id = ?";
        return modify(sql, loginAttempt.getLoginAttemptId());
    }

    @Override
    public int getUnsuccessfulAttemptsSince(Date date, String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) AS count\n" +
                "FROM login_attempt\n" +
                "WHERE (login_attempt_time > ? AND login_successful = FALSE) AND nick_name = ?";
        return count(sql, date, nickName);
    }

    @Override
    public int getSuccessfulAttemptsSince(Date date, String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) AS count\n" +
                "FROM login_attempt\n" +
                "WHERE (login_attempt_time > ? AND login_successful = TRUE) AND nick_name = ?";
        return count(sql, date, nickName);
    }
}
