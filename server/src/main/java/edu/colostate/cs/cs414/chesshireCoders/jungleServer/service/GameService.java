package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.util.List;

public interface GameService {

    Game newGame(String playerOneNickName) throws Exception;

    void updateGame(Game game) throws Exception;

    Game fetchGame(long gameId) throws Exception;

    List<Game> fetchUserGames(long userId) throws Exception;

    List<Invitation> getPlayerReceivedInvites(String nickName, InvitationStatusType statusType) throws Exception;

    void acceptInvitation(long invitationId) throws Exception;

    void rejectInvitation(long invitationId) throws Exception;

    Invitation createInvitation(String senderNickname, String receiverNickName, long gameId) throws Exception;
}
