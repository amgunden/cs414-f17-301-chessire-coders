package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;

import java.sql.SQLException;
import java.util.List;

public interface GameDAO extends GenericDAO<Game, Long> {

    List<Game> findByPlayerOneId(long userId) throws SQLException;

    List<Game> findByPlayerTwoId(long userId) throws SQLException;
}
