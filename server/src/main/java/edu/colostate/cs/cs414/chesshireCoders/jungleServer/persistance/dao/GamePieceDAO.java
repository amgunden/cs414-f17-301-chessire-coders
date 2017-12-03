package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;

import java.sql.SQLException;
import java.util.List;

public interface GamePieceDAO extends GenericDAO<GamePiece, Long> {
    List<GamePiece> findByGameId(long gameId) throws SQLException;

    int deleteByGameId(long gameId) throws SQLException;
}
