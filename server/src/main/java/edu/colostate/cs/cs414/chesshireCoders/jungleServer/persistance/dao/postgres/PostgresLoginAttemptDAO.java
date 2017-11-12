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

    public PostgresLoginAttemptDAO(Connection connection) {
        super(connection);
    }

    @Override
    public LoginAttempt findByPrimaryKey(Long pk) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM login_attempt WHERE user_id = ?";
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
        String sql = "INSERT INTO login_attempt (login_attempt_time, login_successful, user_id) VALUES (?, ?, ?)";
        return add(sql, Long.class, loginAttempt.getAttemptTime(), loginAttempt.isSuccessful(), loginAttempt.getUserId());
    }

    @Override
    public int update(LoginAttempt loginAttempt) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE login_attempt\n" +
                "SET login_attempt_time = ?, login_successful = ?\n" +
                "WHERE user_id = ?";
        return modify(sql, loginAttempt.getAttemptTime(), loginAttempt.isSuccessful());
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
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(LoginAttempt loginAttempt) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM \"user\" WHERE user_id = ?";
        return modify(sql, loginAttempt.getUserId());
    }

    @Override
    public int getUnsuccessfulAttemptsSince(Date date) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) AS count\n" +
                "FROM login_attempt\n" +
                "WHERE login_attempt_time > ? AND login_successful = FALSE";
        return count(sql, date);
    }

    @Override
    public int getSuccessfulAttemptsSince(Date date) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT COUNT(*) AS count\n" +
                "FROM login_attempt\n" +
                "WHERE login_attempt_time > ? AND login_successful = TRUE";
        return count(sql, date);
    }
}
