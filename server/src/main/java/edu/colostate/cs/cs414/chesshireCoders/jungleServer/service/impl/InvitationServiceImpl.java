package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.InvitationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.util.List;


public class InvitationServiceImpl implements InvitationService {

    private DAOManager manager = DAOManager.instance(
            HikariConnectionProvider.getInstance(),
            PostgresDAOManager.class);

    @Override
    public Invitation createInvitation(final String senderNickname, final String receiverNickName, long gameId) throws Exception {
        return manager.executeAtomic(manager -> {
            // retrieve user ID's
            long senderId = manager.getUserDAO()
                    .findByNickName(senderNickname)
                    .getUserId();
            long recipientId = manager.getUserDAO()
                    .findByNickName(receiverNickName)
                    .getUserId();

            // Create invitation and save.
            Invitation invite = new Invitation()
                    .setSenderId(senderId)
                    .setRecipientId(recipientId)
                    .setGameId(gameId);
            manager.getInvitationDAO().create(invite);
            return invite;
        });
    }

    @Override
    public int updateInvitationStatus(long invitationId, InvitationStatusType statusType) throws Exception {
        return manager.execute(manager -> {
            Invitation invite = manager.getInvitationDAO()
                    .findByPrimaryKey(invitationId)
                    .setInvitationStatus(statusType);
            return manager.getInvitationDAO()
                    .update(invite);
        });
    }

    @Override
    public List<Invitation> getPlayerReceivedInvites(long userId) throws Exception {
        return manager.execute(manager -> manager.getInvitationDAO().findByRecipientId(userId));
    }

    @Override
    public List<Invitation> getPlayerReceivedInvites(String nickName) throws Exception {
        return manager.execute(manager -> {
            long userId = manager.getUserDAO()
                    .findByNickName(nickName)
                    .getUserId();
            return manager.getInvitationDAO()
                    .findByRecipientId(userId);
        });
    }

    @Override
    public List<Invitation> getPlayerReceivedInvites(long userId, InvitationStatusType statusType) throws Exception {
        return manager.execute(manager -> manager.getInvitationDAO()
                .findByRecipientId(userId, statusType));
    }

    @Override
    public List<Invitation> getPlayerReceivedInvites(String nickName, InvitationStatusType statusType) throws Exception {
        return manager.execute(manager -> {
            long userId = manager.getUserDAO()
                    .findByNickName(nickName)
                    .getUserId();
            return manager.getInvitationDAO()
                    .findByRecipientId(userId, statusType);
        });
    }
}
