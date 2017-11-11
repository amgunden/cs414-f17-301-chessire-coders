package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    T map(ResultSet rs) throws SQLException;
}
