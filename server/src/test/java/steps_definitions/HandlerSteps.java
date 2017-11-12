package steps_definitions;

import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.Response;
import main.World;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandlerSteps implements En {
    public HandlerSteps(World world) {
        Then("^the client is sent an? (error|success) response$", (String responseType) -> {
            Response response = Response.class.cast(world.getSentMessages().get(0));
            if (responseType.equals("success")) assertTrue(response.isSuccess());
            else if (responseType.equals("error")) assertFalse(response.isSuccess());
        });
    }
}
