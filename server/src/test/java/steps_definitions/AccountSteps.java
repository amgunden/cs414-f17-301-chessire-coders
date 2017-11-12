package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.SessionHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import main.World;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Map;

import static org.junit.Assert.assertFalse;

public class AccountSteps implements En {

    private RegistrationService registrationService = new RegistrationServiceImpl();

    private SessionHandler sessionHandler = new SessionHandler();

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
        Then("^they have been marked as un-registered$", () -> {
            for (Map<String, String> credential : world.getCredentials()) {
                try {
                    assertFalse(registrationService.isRegistered(credential.get("email")));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        When("^they un-register their account$", () -> {
            for (Map<String, String> credential : world.getCredentials()) {
                try {
                    registrationService.unregisterUser(credential.get("email"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
