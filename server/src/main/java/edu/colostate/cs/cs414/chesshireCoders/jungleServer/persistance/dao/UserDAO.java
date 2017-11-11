package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.sql.SQLException;

public interface UserDAO extends GenericDAO<User, Long> {
    
    User findByNickName(String nickName) throws SQLException;
    
}
