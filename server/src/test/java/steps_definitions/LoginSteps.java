package steps_definitions;

import com.esotericsoftware.kryonet.Connection;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.session.LoginManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import helpers.ExceptionHelper;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginSteps implements En {

    private LoginManager loginManager = LoginManager.getManager();
    private List<List<String>> credentials;
    private HashSet<Connection> emailConnectionMap = new HashSet<>();

    ExceptionHelper exceptionHelper = new ExceptionHelper();

    public LoginSteps() {

        When("^they log in with the following credentials:$", (DataTable dataTable) -> {

            credentials = dataTable.asLists(String.class);

            for (List<String> credential : credentials) {
                try {
                    JungleConnection connection = new JungleConnection();
                    loginManager.authenticate(
                            credential.get(0),
                            Crypto.hashSHA256(credential.get(1).getBytes()),
                            connection
                    );

                    emailConnectionMap.add(connection);

                } catch (Exception e) {
                    exceptionHelper.add(e);
                }
            }
        });
        Then("^they are authenticated$", () -> {
            for (Connection connection : emailConnectionMap) {
                try {
                    assertTrue(loginManager.isConnectionAuthorized(connection));
                } catch (LoginManager.InvalidConnectionException e) {
                    exceptionHelper.add(e);
                }
            }
        });
        Then("^they are not authenticated$", () -> {
            for (Connection connection : emailConnectionMap) {
                try {
                    assertFalse(loginManager.isConnectionAuthorized(connection));
                } catch (LoginManager.InvalidConnectionException e) {
                    exceptionHelper.add(e);
                }
            }
        });
        And("^this is their third attempt$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^their account is locked$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        Then("^it fails$", () -> assertFalse(exceptionHelper.getExceptions().isEmpty()));
        Given("^the account does not exist$", () -> exceptionHelper.expectsException());
    }
}
