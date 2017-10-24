package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events.GameEndedEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events.ServerEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.OneTimeRunnableListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.RegisterResponse;

public class NetworkListener {

    /**
     * This method adds listeners to the client that will handle event messages from the server
     *
     * @param client The client object to add the listeners to.
     */
    public static void addEventListeners(Client client) {

        // Server Event Listener
        client.addListener(new FilteredListener<ServerEvent>(ServerEvent.class) {
            @Override
            public void run(Connection connection, ServerEvent received) {

            }
        });

        // Game Ended Event Listener
        client.addListener(new FilteredListener<GameEndedEvent>(GameEndedEvent.class) {
            @Override
            public void run(Connection connection, GameEndedEvent received) {

            }
        });

        client.addListener(new FilteredListener<InvitationEvent>(InvitationEvent.class) {
            @Override
            public void run(Connection connection, InvitationEvent received) {

            }
        });
        client.addListener(new FilteredListener<RegisterRequest>(RegisterRequest.class) {
            @Override
            public void run(Connection connection, RegisterRequest received) {

            }
        });
        
        client.addListener(new OneTimeRunnableListener<RegisterResponse>(RegisterResponse.class, client, () -> {
            
            

        }));
        
        
    }
}
