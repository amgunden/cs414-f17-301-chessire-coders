package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.util.GameStateException;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationAcceptedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetAvailPlayersRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InviteReplyRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetAvailPlayersResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InvitePlayerResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InviteReplyResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.*;

public class InvitationHandler extends Listener {

    private SessionService sessionService = new SessionServiceImpl();
    private GameService gameService = new GameServiceImpl();

    private JungleServer server;

    public InvitationHandler(JungleServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof InvitePlayerRequest) {
            connection.sendTCP(handlePlayerInviteRequest((InvitePlayerRequest) object, connection));
        } else if (object instanceof InviteReplyRequest) {
            connection.sendTCP(handleInvitationReply((InviteReplyRequest) object, connection));
        } else if (object instanceof GetAvailPlayersRequest) {
            connection.sendTCP(handleGetAvailPlayersRequest((GetAvailPlayersRequest) object, connection));
        }
    }

    /**
     * Handle a request to accept or reject an invitation
     */
    private InviteReplyResponse handleInvitationReply(InviteReplyRequest request, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(request, connection)) {
                // if accepted, accept and start game, then player 1
                if(request.isInviteAccepted()) {
                    gameService.acceptInvitation(request.getInvitationId());

                    // send event to client
                    server.sendToTCPWithNickName(
                            new InvitationAcceptedEvent(request.getGameId()),
                            request.getSendingNickName());
                    
                }
                // if rejected, reject and return success to player 2
                else gameService.rejectInvitation(request.getInvitationId());

                return new InviteReplyResponse(request.getGameId()); // defaults to success

            } else return new InviteReplyResponse(UNAUTHORIZED, "You are not authorized to perform this action.");
        } catch (GameStateException e) {
            // If game already started.
            return new InviteReplyResponse(GAME_ALREADY_STARTED, "This game has already been started.");
        } catch (Exception e) {
            e.printStackTrace();
            return new InviteReplyResponse(SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Handle a new request to invite a player to a game
     */
    private InvitePlayerResponse handlePlayerInviteRequest(InvitePlayerRequest request, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(request, connection)) {

                JungleConnection jungleConnection = JungleConnection.class.cast(connection);
                // create new invitation
                Invitation invitation = gameService.createInvitation(
                        jungleConnection.getNickName(),
                        request.getNickname(),
                        request.getGameID());

                // check if receiving player is connected
                //      If connected: send invitation event
                Date now = new Date(System.currentTimeMillis());
                server.sendToTCPWithNickName(new InvitationEvent(invitation).setInvitationTime(now), request.getNickname());
                return new InvitePlayerResponse(); // defaults to success
            } else return new InvitePlayerResponse(UNAUTHORIZED, "You are not authorized to perform this action.");
        } catch (Exception e) {
            e.printStackTrace();
            return new InvitePlayerResponse(SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Handle a new request to invite a player to a game
     */
    private GetAvailPlayersResponse handleGetAvailPlayersRequest(GetAvailPlayersRequest request, Connection connection) {
    	
    	try {
    		if (sessionService.validateSessionRequest(request, connection)) {
    			
    			List<User> users = gameService.getPlayersAvailForInvites(request.getRequestorNickname());
    			List<String> nickNames = new ArrayList<String>();

                for (User user : users) {
                    nickNames.add(user.getNickName());
                }

                return new GetAvailPlayersResponse(nickNames);
    		} else return new GetAvailPlayersResponse(UNAUTHORIZED, "You are not authorized to perform this action.");
        } catch (Exception e) {
            e.printStackTrace();
            return new GetAvailPlayersResponse(SERVER_ERROR, e.getMessage());
        }

    }
}
