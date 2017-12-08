package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.GameDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.GamePieceDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.util.GameStateException;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.*;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType.ACCEPTED;
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
                    .setPlayerOneNickName(user.getNickName())
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
    public void updateGame(String updatingNickName, final Game game) throws Exception {
        if (game == null) throw new IllegalArgumentException("Game cannot be NULL");
        GameStatus status = game.getGameStatus();
        if (status == PENDING)
            throw new IllegalStateException("Game has not been started, it cannot be update");

        manager.executeAtomic((DAOCommand<Void>) manager -> {
            List<GamePiece> updatedPieces = game.getGamePieces();
            if (updatedPieces == null) throw new NullPointerException("Game piece list is NULL");

            if (game.getTurnOfPlayer() == PLAYER_ONE && updatingNickName.equals(game.getPlayerOneNickName()))
                throw new GameStateException("It is not this player's turn");
            if (game.getTurnOfPlayer() == PLAYER_TWO && updatingNickName.equals(game.getPlayerTwoNickName()))
                throw new GameStateException("It is not this player's turn");

            // Update game
            if (status == WINNER_PLAYER_ONE || status == WINNER_PLAYER_TWO || status == DRAW)
                game.setGameEnd(new Date(System.currentTimeMillis()));

            manager.getGameDAO().update(game);

            // delete any pieces missing from the game set
            List<GamePiece> storedPieces = manager.getGamePieceDAO().findByGameId(game.getGameID());

            List<Long> missingIds = new ArrayList<>();

            // find any pieces that are missing in the received List
            for (GamePiece storedPiece : storedPieces) {
                boolean found = false;
                for (GamePiece updatedPiece : updatedPieces) {
                    if (storedPiece.getPieceId() == updatedPiece.getPieceId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) missingIds.add(storedPiece.getPieceId());
            }

            // delete any captured ID's
            for (Long pieceId : missingIds) {
                manager.getGamePieceDAO().delete(pieceId);
            }


            // Update pieces
            for (GamePiece piece : updatedPieces) {
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
    public List<Game> fetchUserGames(String nickName) throws Exception {
        return manager.execute(manager -> {
            List<Game> games = manager.getGameDAO().findAllByNickName(nickName);

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
    public List<Game> fetchUserGamesWithoutPieces(String nickName) throws Exception {
        return manager.execute(manager -> {
            List<Game> games = manager.getGameDAO().findAllByNickName(nickName);

            return games;
        });
    }
    
    @Override
    public List<Game> fetchUserGames(String nickName, GameStatus... statuses) throws Exception {
        return manager.execute(manager -> {
            List<Game> games = manager.getGameDAO().findAllByNickName(nickName, statuses);

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
    public List<Game> fetchUserGamesWithoutPieces(String nickName, GameStatus... statuses) throws Exception {
        return manager.execute(manager -> {
            List<Game> games = manager.getGameDAO().findAllByNickName(nickName, statuses);

            return games;
        });
    }

    @Override
    public String quitGame(String sendingNickName, long gameId) throws Exception {
        return manager.execute(manager -> {

            GameDAO gameDAO = manager.getGameDAO();
            GamePieceDAO gamePieceDAO = manager.getGamePieceDAO();

            // Get the relevant game
            Game game = gameDAO.findByPrimaryKey(gameId);
            if (game == null) throw new Exception("Could not find game.");

            // Delete all referencing pieces, they are not longer needed.
            gamePieceDAO.deleteByGameId(gameId);

            if (game.getGameStatus() != PENDING) {
                // Determine who should win (quitter loses)
                GameStatus status = game.getPlayerOneNickName().equals(sendingNickName) ? WINNER_PLAYER_TWO : WINNER_PLAYER_ONE;

                // Update and save the game
                game.setGameStatus(status);
                game.setGameEnd(new Date(System.currentTimeMillis()));
                gameDAO.update(game);
            } else gameDAO.delete(gameId);

            // Return the nickname of the player who needs to be notified of the game quit
            return sendingNickName.equals(game.getPlayerOneNickName())
                    ? game.getPlayerTwoNickName() : game.getPlayerOneNickName();
        });
    }

    @Override
    public void acceptInvitation(long invitationId) throws Exception {
        Invitation invitation = updateInviteStatus(invitationId, ACCEPTED);
        startGame(invitation);
    }

    @Override
    public void rejectInvitation(long invitationId) throws Exception {
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
                    .setPlayerTwoNickName(invite.getRecipientNickName())
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
            // Create invitation and save.
            Invitation invite = new Invitation()
                    .setSenderNickname(senderNickname)
                    .setRecipientNickName(receiverNickName)
                    .setGameId(gameId)
                    .setInvitationStatus(InvitationStatusType.PENDING);
            long inviteId = manager.getInvitationDAO().create(invite);

            invite.setInvitationId(inviteId);

            return invite;
        });
    }

    @Override
    public List<Invitation> getPlayerReceivedInvites(String nickName, InvitationStatusType statusType) throws Exception {
        return manager.execute(manager -> manager.getInvitationDAO()
                .findByRecipientNickName(nickName, statusType));
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
    
    @Override    
    public List<User> getPlayersAvailForInvites(String nickName) throws Exception {
    	return manager.execute(manager -> manager.getUserDAO()
                .findEveryoneElse(nickName));
    	
    }

}
