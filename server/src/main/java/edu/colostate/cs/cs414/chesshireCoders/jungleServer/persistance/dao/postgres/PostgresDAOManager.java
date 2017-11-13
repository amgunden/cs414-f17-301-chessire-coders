package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.*;

import java.sql.SQLException;

public class PostgresDAOManager extends DAOManager {

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

    @Override
    public GameDAO getGameDAO() throws SQLException {
        return new PostgresGameDAO(getConnection());
    }

    @Override
    public GamePieceDAO getGamePieceDAO() throws SQLException {
        return new PostgresGamePieceDAO(getConnection());
    }
}
