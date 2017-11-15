package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.util.GameStateException;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.ONGOING;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.PENDING;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType.*;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType.*;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_TWO;

public class GameServiceImpl implements GameService {

    private DAOManager manager = DAOManager.instance(
            HikariConnectionProvider.getInstance(),
            PostgresDAOManager.class);

    @Override
    public Game newGame(final String playerOneNickName) throws Exception {
        if (playerOneNickName == null) throw new IllegalArgumentException("nick name cannot be null");

        // find requesting user
        User user = manager.execute(manager -> manager
                .getUserDAO()
                .findByNickName(playerOneNickName));

        if (user == null) throw new Exception("User not found");

        // setup the game.
        return manager.executeAtomic(manager -> {
            // Create the pieces
            List<GamePiece> gamePieces = createPieces();
            // setup the initial game, add the pieces
            Game game = new Game()
                    .setPlayerOneID(user.getUserId())
                    .setGamePieces(gamePieces)
                    .setGameStatus(PENDING);
            long gameId = manager.getGameDAO().create(game);
            for (GamePiece piece : gamePieces) {
                // tie the game id to each piece, then add it
                piece.setGameId(gameId);
                manager.getGamePieceDAO().create(piece);
            }
            game.setGameID(gameId);
            // finally, add the game
            return game;
        });
    }

    @Override
    public void updateGame(long updatingUserId, final Game game) throws Exception {
        if (game == null) throw new IllegalArgumentException("Game cannot be NULL");
        if (game.getGameStatus() == PENDING)
            throw new IllegalStateException("Game has not been started, it cannot be update");

        manager.executeAtomic((DAOCommand<Void>) manager -> {
            List<GamePiece> gamePieces = game.getGamePieces();
            if (gamePieces == null) throw new NullPointerException("Game piece list is NULL");

            if (game.getTurnOfPlayer() == PLAYER_ONE && updatingUserId == game.getPlayerOneID())
                throw new GameStateException("It is not this player's turn");
            if (game.getTurnOfPlayer() == PLAYER_TWO && updatingUserId == game.getPlayerTwoID())
                throw new GameStateException("It is not this player's turn");

            // Update game
            PlayerEnumType player = game.getTurnOfPlayer() == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
            game.setTurnOfPlayer(player);
            manager.getGameDAO().update(game);

            // Update pieces
            for (GamePiece piece : gamePieces) {
                manager.getGamePieceDAO().update(piece);
            }
            return null;
        });
    }

    @Override
    public Game fetchGame(long gameId) throws Exception {
    	return manager.execute(manager -> {
    		Game game = manager.getGameDAO().findByPrimaryKey(gameId);
    		List<GamePiece> pieces = manager
    			.getGamePieceDAO()
    			.findByGameId(gameId);
    		return game.setGamePieces(pieces);
    	});
    }

    @Override
    public List<Game> fetchUserGames(long userId) throws Exception {
        return manager.execute(manager -> {
    		List<Game> games = manager.getGameDAO().findByUserId(userId);
    		
    		for (Game game : games) {
    			List<GamePiece> pieces = manager
    	    			.getGamePieceDAO()
    	    			.findByGameId(game.getGameID());
    	    	game.setGamePieces(pieces);
			}
    		
    		return games;
    	});
    }

    @Override
    public void acceptInvitation(long invitationId) throws Exception {
        Invitation invitation = updateInviteStatus(invitationId, ACCEPTED);
        startGame(invitation);
    }

    @Override
    public void rejectInvitation(long invitationId) throws Exception {
        Invitation invitation = updateInviteStatus(invitationId, REJECTED);
        deleteInvitation(invitationId);
    }

    private Invitation updateInviteStatus(long invitationId, InvitationStatusType type) throws Exception {
        return manager.execute(manager -> {
            // find invitation
            Invitation invitation = manager.getInvitationDAO()
                    .findByPrimaryKey(invitationId);
            // update invitation status
            invitation.setInvitationStatus(type);
            // update DB
            manager.getInvitationDAO().update(invitation);
            return invitation;
        });
    }

    private Game startGame(Invitation invite) throws Exception {
        if (invite == null) throw new IllegalArgumentException("Invite cannot be null");

        return manager.executeAtomic(manager -> {
            Game game = manager.getGameDAO()
                    .findByPrimaryKey(invite.getGameId());

            if (game.getGameStatus() == ONGOING) throw new GameStateException("Game is already started");

            // Update game object with start time, player two ID and status
            game.setGameStart(new Date(System.currentTimeMillis()))
                    .setPlayerTwoID(invite.getRecipientId())
                    .setGameStatus(ONGOING);

            // save to DB
            manager.getGameDAO().update(game);
            return game;
        });
    }

    private Invitation deleteInvitation(long invitationId) throws Exception {
        return manager.execute(manager -> {
            // find invitation
            Invitation invitation = manager.getInvitationDAO()
                    .findByPrimaryKey(invitationId);
            // update invitation status
            // update DB
            manager.getInvitationDAO().delete(invitation.getInvitationId());
            return invitation;
        });
    }
    
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
                    .setSenderNickname(senderNickname)
                    .setRecipientId(recipientId)
                    .setGameId(gameId)
                    .setInvitationStatus(InvitationStatusType.PENDING);
            long inviteId = manager.getInvitationDAO().create(invite);
            
            invite.setInvitationId(inviteId);
            
            return invite;
        });
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

    private List<GamePiece> createPieces() {
        ArrayList<GamePiece> pieces = new ArrayList<>();

        // position (0,0) is top left-most position on board grid.

        // power level 1
        pieces.add(new GamePiece(PLAYER_ONE, RAT, 0, 2));
        pieces.add(new GamePiece(PLAYER_TWO, RAT, 6, 6));

        // power level 2
        pieces.add(new GamePiece(PLAYER_ONE, CAT, 5, 1));
        pieces.add(new GamePiece(PLAYER_TWO, CAT, 1, 7));

        // power level 3
        pieces.add(new GamePiece(PLAYER_ONE, FOX, 4, 2));
        pieces.add(new GamePiece(PLAYER_TWO, FOX, 2, 6));

        // power level 4
        pieces.add(new GamePiece(PLAYER_ONE, DOG, 1, 1));
        pieces.add(new GamePiece(PLAYER_TWO, DOG, 5, 7));

        // power level 5
        pieces.add(new GamePiece(PLAYER_ONE, LEOPARD, 2, 2));
        pieces.add(new GamePiece(PLAYER_TWO, LEOPARD, 4, 6));

        // power level 6
        pieces.add(new GamePiece(PLAYER_ONE, TIGER, 6, 0));
        pieces.add(new GamePiece(PLAYER_TWO, TIGER, 0, 8));

        // power level 7
        pieces.add(new GamePiece(PLAYER_ONE, LION, 0, 0));
        pieces.add(new GamePiece(PLAYER_TWO, LION, 6, 8));

        // power level 8
        pieces.add(new GamePiece(PLAYER_ONE, ELEPHANT, 6, 2));
        pieces.add(new GamePiece(PLAYER_TWO, ELEPHANT, 0, 6));

        return pieces;
    }
}
