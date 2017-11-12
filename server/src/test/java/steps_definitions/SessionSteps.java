package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import helpers.ConnectionHelper;
import helpers.ExceptionHelper;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionSteps implements En {

    private SessionService sessionService = new SessionServiceImpl();
    private ExceptionHelper exceptionHelper = new ExceptionHelper();
    private ConnectionHelper connectionHelper = new ConnectionHelper();

    private List<Map<String, String>> givenCredentials;
    private List<JungleConnection> connections;

    public SessionSteps() {

        When("^they log in with (?:correct|any) credentials:$", (DataTable dataTable) -> {

            givenCredentials = dataTable.asMaps(String.class, String.class);
            connections = new ArrayList<>();

            for (Map<String, String> credential : givenCredentials) {
                try {
                    JungleConnection connection = connectionHelper.createConnection();
                    connections.add(connection);
                    sessionService.authenticate(
                            credential.get("email"),
                            credential.get("password"),
                            connection
                    );
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^they are authenticated$", () -> {
            for (JungleConnection connection : connections) {
                try {
                    assertTrue(sessionService.isAuthorized(connection));
                    assertTrue(connection.isAuthorized());
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^they are not authenticated$", () -> {
            for (JungleConnection connection : connections) {
                try {
                    assertFalse(sessionService.isAuthorized(connection));
                    assertFalse(connection.isAuthorized());
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^their account is locked$", () -> {
            for (Map<String, String> credential : givenCredentials) {
                try {
                    sessionService.isAccountLocked(credential.get("email"));
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^it fails$", () -> assertTrue(exceptionHelper.failed()));
        Given("^the account does not exist$", () -> exceptionHelper.expectsException(AccountNotFoundException.class));
        When("^they log in with (?:incorrect|bad) credentials:$", (DataTable dataTable) -> {
            givenCredentials = dataTable.asMaps(String.class, String.class);
            connections = new ArrayList<>();
            exceptionHelper.expectsException(CredentialException.class);

            for (Map<String, String> credential : givenCredentials) {
                try {
                    JungleConnection connection = new JungleConnection();
                    connections.add(connection);
                    sessionService.authenticate(
                            credential.get("email"),
                            credential.get("password"),
                            connection
                    );
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        When("^they (?:log|are logged) out$", () -> {
            for (JungleConnection connection : connections) {
                try {
                    sessionService.expireSession(connection.getAuthToken().getToken());
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^their session is expired$", () -> {
            for (JungleConnection connection : connections) {
                try {
                    sessionService.isExpired(connection);
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
    }
}
