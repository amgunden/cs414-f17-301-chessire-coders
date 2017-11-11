package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {

    Connection getConnection() throws SQLException;
}
