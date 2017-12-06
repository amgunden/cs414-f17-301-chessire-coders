package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends GenericDAO<User, String> {
    
    User findByNickName(String nickName) throws SQLException;
    
    List<User> findEveryoneElse(String nickName) throws SQLException; 
    
}
