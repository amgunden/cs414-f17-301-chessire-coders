package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.InvitationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.InvitationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InvitePlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.InvitePlayerResponse;

import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.SERVER_ERROR;
import static edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.ResponseStatusCodes.UNAUTHORIZED;

public class InvitationHandler extends Listener {

    private SessionService sessionService = new SessionServiceImpl();
    private InvitationService invitationService = new InvitationServiceImpl();
    private JungleServer server;

    public InvitationHandler(JungleServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof InvitePlayerRequest) {
            connection.sendTCP(handlePlayerInviteRequest((InvitePlayerRequest) object, connection));
        }
    }

    private InvitePlayerResponse handlePlayerInviteRequest(InvitePlayerRequest request, Connection connection) {
        try {
            if (sessionService.validateSessionRequest(request, connection)) {

                JungleConnection jungleConnection = JungleConnection.class.cast(connection);
                // create new invitation
                Invitation invitation = invitationService.createInvitation(
                        jungleConnection.getNickName(),
                        request.getNickname(),
                        request.getGameID());

                // check if receiving player is connected
                //      If connected: send invitation event
                server.sendToTCPWithNickName(new InvitationEvent(invitation), request.getNickname());
                return new InvitePlayerResponse(); // defaults to success
            } else return new InvitePlayerResponse(UNAUTHORIZED, "You are not authorized to perform this action.");
        } catch (Exception e) {
            e.printStackTrace();
            return new InvitePlayerResponse(SERVER_ERROR, "An error occurred checking session validity.");
        }
    }
}
