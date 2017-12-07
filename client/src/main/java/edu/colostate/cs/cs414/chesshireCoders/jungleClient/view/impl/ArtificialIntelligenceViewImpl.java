package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai.Move;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai.MoveFinder;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.ControllerFactory;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.GameBoardController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.InvitesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.BaseView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static edu.colostate.cs.cs414.chesshireCoders.jungleClient.ai.MoveFinder.AlgorithmType.NEGA_MAX;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus.*;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_TWO;
import static java.util.logging.Level.*;


public class ArtificialIntelligenceViewImpl extends BaseView implements Runnable {

    private static final String BOT_EMAIL = "bot@sky.net";
    private static final String BOT_PASS = "55Y4*4&f1$65eh7K!N3A2jSd";
    // Assumes the AI is always player two.
    private static final PlayerEnumType THIS_PLAYER = PLAYER_TWO;
    // Number of available threads to use for the AI.
    private static final int AI_THREAD_COUNT = 10;
    // How hard the AI should look for an ideal move.
    private static final int AI_DEPTH = 100;
    // The max number of login to attempt before giving up.
    private static int loginRetries = 3;
    // our data models
    private final AccountModel accountModel = AccountModel.getInstance();
    // initialize logger
    private Logger logger;
    // The executor service that queue's up the AI jobs to run.
    // We can add any number of AI threads to the service, but only $AI_THREAD_COUNT
    // jobs will be executing at any given time.
    private ExecutorService executorService = Executors.newFixedThreadPool(AI_THREAD_COUNT);

    // The controllers. We leave the uninitialized so that they don't login any unwanted network listeners.
    private LoginController loginController;
    private HomeController homeController;
    // Listens for any new invites, automatically accepts them.
    private final ListChangeListener<Invitation> newInviteListener = (ListChangeListener<Invitation>) c -> {
        while (c.next()) {
            for (Invitation invitation : c.getAddedSubList()) {
                try {
                    homeController.sendAcceptInvite(invitation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private GameBoardController gameBoardController;
    private GamesModel gamesModel = GamesModel.getInstance();
    // Listens for new games, and game updates.
    private final ListChangeListener<JungleGame> gameUpdateListener = (ListChangeListener<JungleGame>) c -> {
        while (c.next()) {
            // the set() operation used in GamesModel to update a game is the equivalent of removing then adding a game.
            // So we iterate through added games.
            for (JungleGame game : c.getAddedSubList()) {
                if (isGameOver(game)) {
                    removeGame(game);
                    logger.log(INFO, String.format("Game '%s' is over.", game));
                } else if (game.getTurnOfPlayer() == THIS_PLAYER) {
                    makeSmartMove(game);
                }
            }
        }
    };
    private InvitesModel invitesModel = InvitesModel.getInstance();
    // Listens for the login success property to be changed.
    private final ChangeListener<Boolean> loginSuccessListener = (observable, oldValue, newValue) -> {
        if (newValue != null && newValue) loginSuccess();
        else if (newValue != null) loginFailure();
    };

    {
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(FINER);
    }

    /**
     * Removes a game from the game model.
     * The bot will no longer track that game (assuming it is not updated on the server)
     */
    private void removeGame(JungleGame game) {
        gamesModel.removeGame(game.getGameID());
    }

    /**
     * Checks the game status and returns true if the status is set to a win/draw status
     */
    private boolean isGameOver(JungleGame game) {
        GameStatus status = game.getGameStatus();
        logger.log(FINER, String.format("Status of game '%s' is '%s'", game, status.name()));
        return status == WINNER_PLAYER_ONE || status == WINNER_PLAYER_TWO || status == DRAW;
    }

    /**
     * find and make a super smart move.
     */
    private void makeSmartMove(final JungleGame game) {
        logger.log(INFO, String.format("Searching for best move in game '%s'", game));

        // start new thread to find best move
        executorService.submit(() -> {
            try {
                logger.log(INFO, String.format("Starting search on game '%s' with depth '%d'", game, AI_DEPTH));
                MoveFinder ai = MoveFinder.getMoveFinder(game, AI_DEPTH, NEGA_MAX);
                ai.run();
                Move bestMove = ai.getBestMove();
                if (bestMove == null) throw new RuntimeException("Could not find move.");
                logger.log(INFO, String.format("Search ended on game '%s' with result '%s'", game, AI_DEPTH));
                MoveFinder.makeMove(bestMove, game);


                logger.log(FINE, String.format("Updating game: %s", game));
                gameBoardController.updateGame(game);
            } catch (Exception e) {
                e.printStackTrace(); // TODO: handle error more better.
            }
        });
    }

    /**
     * If login failed, try again until max retries met (loginRetries == 0)
     */
    private void loginFailure() {
        logger.log(WARNING, "Login failed.");
        if (loginRetries > 0) login();
        else die("Max login attempts reached. Exiting.");
    }

    /**
     * Cleans up the login listener and adds listeners for new invites and games so that
     * they can be responded to.
     */
    private void loginSuccess() {
        logger.log(INFO, "Login was successful.");

        // remove the listener for login success
        accountModel.loginSuccessProperty().removeListener(loginSuccessListener);

        // dispose of login controller
        loginController.dispose();
        loginController = null;

        // add listeners for new invitations and game updates.
        gamesModel.getCurrentGames().addListener(gameUpdateListener);
        invitesModel.getPendingReceivedInvites().addListener(newInviteListener);

        // Initialize controllers
        homeController = ControllerFactory.getHomeController(this);
        gameBoardController = ControllerFactory.getGameBoardController(this);
    }

    /**
     * Creates email, nickName, and password and attempts to login with the server.
     */
    private void login() {
        try {
            logger.log(INFO, "Sending login request.");
            loginController.sendLogin(BOT_EMAIL, Crypto.hashSHA256(BOT_PASS.getBytes()));
            loginRetries--;
        } catch (Exception e) {
            e.printStackTrace();
            loginFailure();
        }
    }

    /**
     * Called on shutdown to close up any open resources.
     */
    private void shutdown() {

        // logout this bot
        logout();

        logger.log(INFO, "Cleaning up listeners.");

        // cleanup controllers
        if (gameBoardController != null) gameBoardController.dispose();
        if (loginController != null) loginController.dispose();
        if (homeController != null) homeController.dispose();

        // cleanup model listeners
        if (accountModel != null) accountModel.loginSuccessProperty().removeListener(loginSuccessListener);
        if (gamesModel != null) gamesModel.getCurrentGames().removeListener(gameUpdateListener);
        if (invitesModel != null) invitesModel.getPendingReceivedInvites().removeListener(newInviteListener);
    }

    /**
     * Called as part of the cleanup/shutdown process to logout this bot.
     */
    private void logout() {
        try {
            logger.log(INFO, "Logging out bot.");
            homeController.sendLogout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * In case it gets too smart....
     */
    private void die(String message) {
        System.err.println(message); // print the error message
        Thread.dumpStack(); // print the current stack trace
        System.exit(1);
    }

    @Override
    public void showError(String message) {
        logger.log(SEVERE, message);
    }

    @Override
    public void showWarning(String message) {
        logger.log(WARNING, message);
    }

    @Override
    public void showInfo(String message) {
        logger.log(INFO, message);
    }

    @Override
    public void run() {
        // Add some actions to execute on system exit.
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(this::shutdown));

        // Initialize the login controller.
        loginController = ControllerFactory.getLoginController(this);

        // login login listener
        accountModel.loginSuccessProperty().addListener(loginSuccessListener);

        // send login info
        login();
    }
}
