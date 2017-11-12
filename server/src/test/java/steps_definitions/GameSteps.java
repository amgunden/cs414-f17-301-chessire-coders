package steps_definitions;

import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.GameHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.GameService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.CreateGameRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.CreateGameResponse;
import main.World;
import org.junit.Assert;

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
        And("^the games? (?:was|were)( not)? created$", (String not) -> {
            for (Object response : world.getSentMessages(CreateGameResponse.class)) {
                CreateGameResponse createGameResponse = CreateGameResponse.class.cast(response);
                try {
                    Game game = gameService.fetchGame(createGameResponse.getGameID());
                    if (not != null) Assert.assertNull(game);
                    else Assert.assertNotNull(game);
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });
    }
}
