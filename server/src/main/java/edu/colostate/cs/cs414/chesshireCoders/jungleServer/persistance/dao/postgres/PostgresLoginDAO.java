package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * A class for pushing/pulling information about Login's from the database.
 */
public class PostgresLoginDAO extends BaseDAO<Login, Long> implements LoginDAO {

    private static final RowMapper<Login> LOGIN_ROW_MAPPER = rs -> {
        Login login = new Login();
        login.setUserID(rs.getInt("user_id"));
        login.setEmail(rs.getString("email"));
        login.setHashedPass(rs.getString("hashed_pass"));
        login.setIsLocked(rs.getBoolean("account_locked"));
        return login;
    };

    public PostgresLoginDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Login findByPrimaryKey(Long userId) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT *\n" +
                "FROM public.\"login\"\n" +
                "WHERE \"login\".\"user_id\" = ?";
        return query(sql, LOGIN_ROW_MAPPER, userId).get(0);
    }

    public Login findByEmail(String email) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT *\n" +
                "FROM public.\"login\"\n" +
                "WHERE \"login\".\"email\" = ?";
        List<Login> logins = query(sql, LOGIN_ROW_MAPPER, email);
        return logins.isEmpty() ? null : logins.get(0);
    }

    @Override
    public Long create(Login login) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO public.\"login\" (\"user_id\", \"email\", \"hashed_pass\") VALUES (?, ?, ?)";
        return add(sql, Long.class, login.getUserID(), login.getEmail(), login.getHashedPass());
    }

    @Override
    public List<Login> findAll() throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM public.\"login\"";
        return query(sql, LOGIN_ROW_MAPPER);
    }

    @Override
    public int update(Login login) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE public.\"login\" SET \"hashed_pass\" = ?, \"account_locked\" = ? WHERE \"email\" = ?";
        return modify(sql, login.getHashedPass(), login.isLocked(), login.getEmail());
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
        //language=PostgreSQL
        String sql = "DELETE FROM public.\"login\" WHERE \"user_id\" = ?";
        return modify(sql, aLong);
    }


    public int delete(Login login) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM public.\"login\" WHERE \"email\" = ?";
        return modify(sql, login.getEmail());
    }
}
