package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.util.List;

public interface GameService {

    Game newGame(String playerOneNickName) throws Exception;

    void updateGame(String updatingNickName, Game game) throws Exception;

    Game fetchGame(long gameId) throws Exception;

    List<Game> fetchUserGames(String nickName) throws Exception;
    List<Game> fetchUserGamesWithoutPieces(String nickName) throws Exception;
    
    List<Game> fetchUserGames(String nickName, GameStatus... statuses) throws Exception;
    List<Game> fetchUserGamesWithoutPieces(String nickName, GameStatus... statuses) throws Exception;

    String quitGame(String sendingNickName, long gameId) throws Exception;

    List<Invitation> getPlayerReceivedInvites(String nickName, InvitationStatusType statusType) throws Exception;

    void acceptInvitation(long invitationId) throws Exception;

    void rejectInvitation(long invitationId) throws Exception;

    Invitation createInvitation(String senderNickname, String receiverNickName, long gameId) throws Exception;
}
