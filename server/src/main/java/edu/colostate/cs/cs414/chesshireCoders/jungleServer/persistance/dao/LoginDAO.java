package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;

import java.sql.SQLException;

public interface LoginDAO extends GenericDAO<Login, Long> {

    Login findByEmail(String email) throws SQLException;

}
