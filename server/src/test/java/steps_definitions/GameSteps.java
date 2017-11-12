package steps_definitions;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.GameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import main.World;

public class GameSteps implements En {

    private GameHandler gameHandler = new GameHandler();
    private GameService gameService = new GameServiceImpl();

    public GameSteps(World world) {
        When("^they (?:create|make) a game$", () -> {
            for (JungleConnection connection : world.getConnections()) {
                CreateGameRequest request = new CreateGameRequest();
                request.setAuthToken(connection.getAuthToken());
                gameHandler.received(connection, request);
            }
        });
        And("^the games? (?:was|were) created$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
