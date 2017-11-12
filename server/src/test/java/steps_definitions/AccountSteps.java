package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import helpers.ExceptionHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;

public class AccountSteps implements En {

    private ExceptionHelper exceptionHelper = new ExceptionHelper();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();

    private List<Map<String, String>> credentials;

    public AccountSteps() {
        Given("^the following accounts exist:$", (DataTable dataTable) -> {
            credentials = dataTable.asMaps(String.class, String.class);
            for (Map<String, String> credential : credentials) {
                try {
                    registrationService.registerUser(
                            credential.get("nick name"),
                            credential.get("email"),
                            credential.get("password")
                    );
                } catch (SQLException e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^they have been marked as un-registered$", () -> {
            for (Map<String, String> credential : credentials) {
                try {
                    assertFalse(registrationService.isRegistered(credential.get("email")));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        When("^they un-register their account$", () -> {
            for (Map<String, String> credential : credentials) {
                try {
                    registrationService.unregisterUser(credential.get("email"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
