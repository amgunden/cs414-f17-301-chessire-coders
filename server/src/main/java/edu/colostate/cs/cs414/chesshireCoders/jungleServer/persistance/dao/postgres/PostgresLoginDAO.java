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
public class PostgresLoginDAO extends BaseDAO<Login, String> implements LoginDAO {

    private static final RowMapper<Login> LOGIN_ROW_MAPPER = rs -> {
        Login login = new Login();
        login.setNickName(rs.getString("nick_name"));
        login.setEmail(rs.getString("email"));
        login.setHashedPass(rs.getString("hashed_pass"));
        login.setIsLocked(rs.getBoolean("account_locked"));
        return login;
    };

    PostgresLoginDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Login findByPrimaryKey(String email) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT *\n" +
                "FROM login\n" +
                "WHERE email = ?";
        List<Login> logins = query(sql, LOGIN_ROW_MAPPER, email);
        return logins.isEmpty() ? null : logins.get(0);
    }

    @Override
    public Login findByNickName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT *\n" +
                "FROM login\n" +
                "WHERE nick_name = ?";
        List<Login> logins = query(sql, LOGIN_ROW_MAPPER, nickName);
        return logins.isEmpty() ? null : logins.get(0);
    }

    @Override
    public String create(Login login) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO login (nick_name, email, hashed_pass) VALUES (?, ?, ?)";
        return add(sql, String.class, login.getNickName(), login.getEmail(), login.getHashedPass());
    }

    @Override
    public List<Login> findAll() throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM login";
        return query(sql, LOGIN_ROW_MAPPER);
    }

    @Override
    public int update(Login login) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE login SET hashed_pass = ?, account_locked = ? WHERE email = ?";
        return modify(sql, login.getHashedPass(), login.isLocked(), login.getEmail());
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param email Primary key
     * @return rows affected (should only be 1)
     */
    @Override
    public int delete(String email) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM login WHERE email = ?";
        return modify(sql, email);
    }


    public int delete(Login login) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM login WHERE email = ?";
        return modify(sql, login.getEmail());
    }
}
