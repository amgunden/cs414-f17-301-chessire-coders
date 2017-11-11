package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

import java.sql.SQLException;

public interface DAOCommand<T> {
    T execute(final DAOManager manager) throws SQLException;
}

