package edu.colostate.cs.cs414.chesshireCoders.jungleServer.handlers;

import static edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.ResponseStatusCodes.SERVER_ERROR;

import java.sql.SQLException;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.GetPlayerRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.GetPlayerResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.Response;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects.PlayerDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.Player;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleServer;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PlayerColor;

public class GetPlayerHandler extends AbstractRequestHandler{

	public GetPlayerHandler(JungleServer server) {
		super(server);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addListeners() {
		// GetPieceLocation Listener
		server.addListener(new Listener.ThreadedListener(
                new FilteredListener<GetPlayerRequest>(GetPlayerRequest.class) {
                    @Override
                    public void run(Connection connection, GetPlayerRequest received) {
                        connection.sendTCP(handleGetPlayer(received));
                    }
                }));
		
	}
	
	// Method will be using playerDAO object and getPlayersByUserId which returns a List<Player>.
	// For now i'm just returning the first player in the set.
	// I need to verify when this will be used. Whether it is just to get user info from the userID or something else
	// Maybe change the DAO to just return 1 player
	private Response handleGetPlayer(GetPlayerRequest request) {
		PlayerDAO playerDAO = new PlayerDAO();
		try {
            try {

                playerDAO.getConnection();
                List<Player> players = playerDAO.getPlayersByUserId(request.getPlayerID());
                
				int playerID = players.get(0).getUserId();
				String playerName = "name Placeholder";
				PlayerColor playerColor = players.get(0).getColor();

                GetPlayerResponse response = new GetPlayerResponse(playerID, playerName, playerColor);
				
                return response;

            } finally {
                playerDAO.closeConnection();
            }
        } catch (SQLException e) {
            return new GetPlayerResponse(SERVER_ERROR, e.getMessage());
        }		
	}

}
