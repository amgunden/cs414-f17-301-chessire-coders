package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.GamesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.InvitesModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.BoardUpdateEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.GameEndedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationAcceptedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.*;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType;

import java.io.IOException;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_ONE;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PlayerEnumType.PLAYER_TWO;

public class HomeControllerImpl extends BaseController implements HomeController {

    private final JungleClient client = JungleClient.getInstance();
    private final Listener listener = new Listener.ThreadedListener(new HomeListener());

    private final GamesModel gamesModel = GamesModel.getInstance();
    private final InvitesModel invitesModel = InvitesModel.getInstance();
    private final AccountModel accountModel = AccountModel.getInstance();

    private boolean watchingForGame = false;
    private long watchForGameId;

    public HomeControllerImpl(View view) {
        super(view);
        client.addListener(listener);
    }

    @Override
    public void dispose() {
        client.removeListener(listener);
    }

    @Override
    public void sendCreateGame() throws IOException {
        CreateGameRequest request = new CreateGameRequest(AccountModel.getInstance().getToken());
        client.sendMessage(request);
    }

    @Override
    public void fetchPlayerGames() {
        // TODO Fetch the user's games from the server.
    }

    @Override
    public void sendGetGame(long gameId) throws IOException {
        GetGameRequest request = new GetGameRequest(AccountModel.getInstance().getToken(), gameId);
        client.sendMessage(request);
    }

    @Override
    public void sendAcceptInvite(Invitation invite) throws IOException {
        sendInviteResponse(invite, true);
        watchFor(invite.getGameId());
    }

    @Override
    public void sendRejectInvite(Invitation invite) throws IOException {
        sendInviteResponse(invite, false);
    }

    @Override
    public void sendUnregister() throws IOException {
        UnRegisterRequest request = new UnRegisterRequest();
        request.setAuthToken(AccountModel.getInstance().getToken());
        client.sendMessage(request);
    }

    @Override
    public void sendLogout() throws IOException {
        client.sendMessage(new LogoutRequest(accountModel.getToken()));
    }

    private void sendInviteResponse(Invitation invite, boolean accepted) throws IOException {
        InviteReplyRequest inviteReplyRequest = new InviteReplyRequest(AccountModel.getInstance().getToken(), accepted, invite.getInvitationId());
        inviteReplyRequest.setGameId(invite.getGameId());
        inviteReplyRequest.setSendingNickName(invite.getSenderNickname());
        client.sendMessage(inviteReplyRequest);
    }

    /**
     * Called when this player has been invited by another player
     */
    private void handleInvitationEvent(InvitationEvent invitationEvent) {
        // TODO: notify observers that a new invitation has been received
        invitesModel.addInvitation(invitationEvent.getInvite());
        invitesModel.notifyObservers(invitationEvent);
    }

    /**
     * Called when the server notifies this client that a sent invitation has been accepted
     */
    private void handleInvitationAcceptedEvent(InvitationAcceptedEvent event) throws IOException {
        client.sendMessage(new GetGameRequest(accountModel.getToken(), event.getGameId()));
    }

    /**
     * Called when server responds to a request made by this client to invite another player to a game
     */
    private void handleInvitePlayerResponse(InvitePlayerResponse response) {
        if (!response.isSuccess()) view.showError(response.getErrMsg());
    }

    /**
     * Called when the server responds to this clients acceptance of an invitation
     */
    private void handleAcceptedInviteResponse(InviteReplyResponse response) throws IOException {
        if (response.isSuccess()) {
            sendGetGame(response.getGameID());
        }
    }

    /**
     * Update list of games and set the active game.
     */
    private void handleCreateGameResponse(CreateGameResponse response) throws IOException {
        if (response.isSuccess()) {
            sendGetGame(response.getGameID());
            watchFor(response.getGameID());
        } else {
            view.showError("Server failed to create game.");
        }
    }

    private void watchFor(long gameID) {
        this.watchForGameId = gameID;
        this.watchingForGame = true;
    }

    /**
     * Update the list of games in the background, do not change active game
     */
    private void handleGetGameResponse(GetGameResponse response) {
        // Add/Update the game in the model
        JungleGame jGame = new JungleGame(response.getGame());
        PlayerEnumType viewingPlayer = getViewingPlayer(jGame);
        jGame.setViewingPlayer(viewingPlayer);
        gamesModel.updateOrAddGame(jGame);

        // Check to see if we're watching for this game so we can change the active game if needed
        if (watchingForGame && watchForGameId == jGame.getGameID()) {
            gamesModel.setActiveGame(jGame);
            watchingForGame = false;
        }
    }

    private PlayerEnumType getViewingPlayer(JungleGame jGame) {
        return accountModel.getNickName().equals(jGame.getPlayerOneNickName()) ? PLAYER_ONE : PLAYER_TWO;
    }

    /**
     * Notify model observers if error occurred.
     */
    private void handleUpdateGameResponse(UpdateGameResponse response) {
        if (!response.isSuccess()) view.showError(response.getErrMsg());
    }

    private void handleGameEndedEvent(GameEndedEvent gameEndedEvent) throws IOException {
        long gameID = gameEndedEvent.getGameID();
        JungleGame game = gamesModel.findById(gameID);
        if (game != null) {
            sendGetGame(gameID);
        }
    }

    private void handleBoardUpdateEvent(BoardUpdateEvent boardUpdateEvent) throws IOException {
        long gameID = boardUpdateEvent.getGameId();
        JungleGame game = gamesModel.findById(gameID);
        if (game != null) {
            sendGetGame(gameID);
        }
    }

    private class HomeListener extends Listener {
        @Override
        public void received(Connection connection, Object received) {
            try {
                // handle a new incoming invitation
                if (received instanceof InvitationEvent) {
                    handleInvitationEvent((InvitationEvent) received);
                }
                // handle server's response to sending a new invitation to a player
                else if (received instanceof InvitePlayerResponse) {
                    handleInvitePlayerResponse((InvitePlayerResponse) received);
                }
                // handle server's response to sending a response to an invitation.
                else if (received instanceof InviteReplyResponse) {
                    handleAcceptedInviteResponse((InviteReplyResponse) received);
                }
                // handle another player accepting this player's invitation.
                else if (received instanceof InvitationAcceptedEvent) {
                    handleInvitationAcceptedEvent((InvitationAcceptedEvent) received);
                }
                // handle create game response
                else if (received instanceof CreateGameResponse) {
                    handleCreateGameResponse((CreateGameResponse) received);
                }
                // handle get game response
                else if (received instanceof GetGameResponse) {
                    handleGetGameResponse((GetGameResponse) received);
                }
                // handle update game response
                else if (received instanceof UpdateGameResponse) {
                    handleUpdateGameResponse((UpdateGameResponse) received);
                }
                // handle game ended event
                else if (received instanceof BoardUpdateEvent) {
                    handleBoardUpdateEvent((BoardUpdateEvent) received);
                }
                // handle game ended event
                else if (received instanceof GameEndedEvent) {
                    handleGameEndedEvent((GameEndedEvent) received);
                }
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }
    }
}
