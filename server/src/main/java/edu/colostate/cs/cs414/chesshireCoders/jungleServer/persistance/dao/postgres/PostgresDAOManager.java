package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.ConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginAttemptDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserSessionDAO;

import java.sql.SQLException;

public class PostgresDAOManager extends DAOManager {

    public PostgresDAOManager() {
        super();
    }

    public PostgresDAOManager(ConnectionProvider provider) {
        super.setProvider(provider);
    }

    @Override
    public LoginDAO getLoginDAO() throws SQLException {
        return new PostgresLoginDAO(getConnection());
    }

    @Override
    public UserDAO getUserDAO() throws SQLException {
        return new PostgresUserDAO(getConnection());
    }

    @Override
    public LoginAttemptDAO getLoginAttemptDAO() throws SQLException {
        return new PostgresLoginAttemptDAO(getConnection());
    }

    @Override
    public UserSessionDAO getUserSessionDAO() throws SQLException {
        return new PostgresUserSessionDAO(getConnection());
    }
}
