package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.ONGOING;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.PENDING;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType.*;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerOwnerType.PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerOwnerType.PLAYER_TWO;

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
    public Game startGame(long gameId, final String playerTwoNickName) throws Exception {
        if (playerTwoNickName == null) throw new IllegalArgumentException("Nick name cannot be null");

        return manager.executeAtomic(manager -> {
            // Find the user to be player two
            User playerTwo = manager.getUserDAO().findByNickName(playerTwoNickName);
            if (playerTwo == null) throw new Exception(String.format("User '%s' was not found", playerTwoNickName));

            // Find and update associated game.
            Game game = manager.getGameDAO().findByPrimaryKey(gameId)
                    .setGameStart(new Date(System.currentTimeMillis()))
                    .setPlayerTwoID(playerTwo.getUserId())
                    .setGameStatus(ONGOING);
            manager.getGameDAO().update(game);
            return game;
        });
    }

    @Override
    public void updateGame(final Game game) throws Exception {
        if (game == null) throw new IllegalArgumentException("Game cannot be NULL");
        if (game.getGameStatus() == PENDING)
            throw new IllegalStateException("Game has not been started, it cannot be update");

        manager.executeAtomic((DAOCommand<Void>) manager -> {
            List<GamePiece> gamePieces = game.getGamePieces();
            if (gamePieces == null) throw new NullPointerException("Game piece list is NULL");

            manager.getGameDAO().update(game);
            for (GamePiece piece : gamePieces) {
                manager.getGamePieceDAO().update(piece);
            }
            return null;
        });
    }

    @Override
    public Game fetchGame(long gameId) throws Exception {
        return manager.execute(manager -> manager.getGameDAO().findByPrimaryKey(gameId));
    }

    @Override
    public List<Game> fetchUserGames(long userId) throws Exception {
        return manager.execute(manager -> manager.getGameDAO().findByUserId(userId));
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
