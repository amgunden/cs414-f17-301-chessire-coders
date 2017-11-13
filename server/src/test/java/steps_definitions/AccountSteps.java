package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.RegistrationHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import main.World;
import org.junit.Assert;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;

public class AccountSteps implements En {

    private RegistrationService registrationService = new RegistrationServiceImpl();

    private RegistrationHandler registrationHandler = new RegistrationHandler();

    public AccountSteps(final World world) {
        Given("^the following accounts exist:$", (DataTable dataTable) -> {
            world.setCredentials(dataTable.asMaps(String.class, String.class));
            for (Map<String, String> credential : world.getCredentials()) {
                try {
                    registrationService.registerUser(
                            credential.get("nick name"),
                            credential.get("email"),
                            credential.get("password"));
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });
        Given("^a user has not created an account$", () ->
                world.expectsException(AccountNotFoundException.class));
        Then("^they have been (?:marked as) un-registered$", () -> {
            for (Map<String, String> credential : world.getCredentials()) {
                try {
                    assertFalse(registrationService.isRegistered(credential.get("email")));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        When("^they un-register their accounts?$", () -> {
            List<Map<String, String>> credentials = world.getCredentials();
            List<JungleConnection> connections = world.getConnections();
            if (connections.size() != credentials.size()) Assert.fail("Connection credential size mis-match");

            for (int i = 0; i < credentials.size(); ++i) {
                UnRegisterRequest request = new UnRegisterRequest()
                        .setEmail(credentials.get(i).get("email"))
                        .setAuthToken(connections.get(i).getAuthToken());
                registrationHandler.received(connections.get(i), request);
            }
        });
    }
}
