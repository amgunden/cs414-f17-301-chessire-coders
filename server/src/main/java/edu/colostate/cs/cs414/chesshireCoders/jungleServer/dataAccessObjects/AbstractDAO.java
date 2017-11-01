package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import java.sql.Connection;

public class AbstractDAO {

    protected Connection connection;

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }
}
