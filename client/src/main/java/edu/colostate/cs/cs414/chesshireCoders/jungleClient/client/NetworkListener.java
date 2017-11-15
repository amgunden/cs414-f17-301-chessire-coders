package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.BoardUpdateEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.GameEndedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationAcceptedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.ServerEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;

public class NetworkListener {
	
	
    /**
     * This method adds listeners to the client that will handle event messages from the server
     *
     * @param client The client object to add the listeners to.
     */
    public static void addEventListeners(JungleClient client) {
    	
        // Server Event Listener
        client.addListener(new ThreadedListener(new FilteredListener<ServerEvent>(ServerEvent.class) {
            @Override
            public void run(Connection connection, ServerEvent received) {

            }
        }));

        // Game Ended Event Listener
        client.addListener(new ThreadedListener(new FilteredListener<GameEndedEvent>(GameEndedEvent.class) {
            @Override
            public void run(Connection connection, GameEndedEvent received) {
            		
            }
        }));

        // Invitation Event
        client.addListener(new ThreadedListener(new FilteredListener<InvitationEvent>(InvitationEvent.class) {
            @Override
            public void run(Connection connection, InvitationEvent received) {
            	InviteManager.getInstance().addInvitationEvent(received);
            }
        }));
        
     // InvitationAccepted Event
        client.addListener(new ThreadedListener(new FilteredListener<InvitationAcceptedEvent>(InvitationAcceptedEvent.class) {
            @Override
            public void run(Connection connection, InvitationAcceptedEvent received) {
            	GamesManager.getInstance().handleBoardUpdateEvent(received.getGameId());
            }
        }));
        
        // Board Update Event
        client.addListener(new ThreadedListener(new FilteredListener<BoardUpdateEvent>(BoardUpdateEvent.class) {
            @Override
            public void run(Connection connection, BoardUpdateEvent received) {
            	GamesManager.getInstance().handleBoardUpdateEvent(received.getGameId());
            }
        }));
    }
}
