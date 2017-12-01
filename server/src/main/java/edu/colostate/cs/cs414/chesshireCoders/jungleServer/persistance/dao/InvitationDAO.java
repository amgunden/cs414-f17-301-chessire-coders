package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.sql.SQLException;
import java.util.List;

public interface InvitationDAO extends GenericDAO<Invitation, Long> {

    List<Invitation> findBySenderNickName(String nickName) throws SQLException;

    List<Invitation> findByRecipientNIckName(String nickName) throws SQLException;

    List<Invitation> findByRecipientNickName(String nickName, InvitationStatusType statusType) throws SQLException;

    List<Invitation> findByGameId(long gameId) throws SQLException;
}
