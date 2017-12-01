package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameDAO extends GenericDAO<Game, Long> {

    List<Game> findByPlayerOneNickName(String nickName) throws SQLException;

    List<Game> findByPlayerTwoNickName(String nickName) throws SQLException;

    List<Game> findAllByNickName(String nickName) throws SQLException;
}
