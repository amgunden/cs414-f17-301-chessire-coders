package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.util.List;

public interface InvitationService {

    Invitation createInvitation(String senderNickname, String receiverNickName, long gameId) throws Exception;

    int updateInvitationStatus(long invitationId, InvitationStatusType statusType) throws Exception;

    List<Invitation> getPlayerReceivedInvites(long userId) throws Exception;

    List<Invitation> getPlayerReceivedInvites(String nickName) throws Exception;

    List<Invitation> getPlayerReceivedInvites(long userId, InvitationStatusType statusType) throws Exception;

    List<Invitation> getPlayerReceivedInvites(String nickName, InvitationStatusType statusType) throws Exception;
}
