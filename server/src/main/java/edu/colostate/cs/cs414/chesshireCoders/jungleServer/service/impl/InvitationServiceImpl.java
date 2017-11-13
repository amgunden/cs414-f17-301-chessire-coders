package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.util.List;


public class InvitationServiceImpl {

    private DAOManager manager = DAOManager.instance(
            HikariConnectionProvider.getInstance(),
            PostgresDAOManager.class);



    public int updateInvitationStatus(long invitationId, InvitationStatusType statusType) throws Exception {
        return manager.execute(manager -> {
            Invitation invite = manager.getInvitationDAO()
                    .findByPrimaryKey(invitationId)
                    .setInvitationStatus(statusType);
            return manager.getInvitationDAO()
                    .update(invite);
        });
    }

    public List<Invitation> getPlayerReceivedInvites(long userId) throws Exception {
        return manager.execute(manager -> manager.getInvitationDAO().findByRecipientId(userId));
    }

    public List<Invitation> getPlayerReceivedInvites(String nickName) throws Exception {
        return manager.execute(manager -> {
            long userId = manager.getUserDAO()
                    .findByNickName(nickName)
                    .getUserId();
            return manager.getInvitationDAO()
                    .findByRecipientId(userId);
        });
    }

    public List<Invitation> getPlayerReceivedInvites(long userId, InvitationStatusType statusType) throws Exception {
        return manager.execute(manager -> manager.getInvitationDAO()
                .findByRecipientId(userId, statusType));
    }


}
