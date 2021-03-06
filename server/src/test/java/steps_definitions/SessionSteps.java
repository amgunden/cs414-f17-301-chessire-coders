package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.handler.SessionHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import main.World;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.CredentialException;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionSteps implements En {

    private SessionService sessionService = new SessionServiceImpl();

    private SessionHandler sessionHandler = new SessionHandler();

    public SessionSteps(final World world) {

        When("^they log in with (correct|incorrect|nonexistent) credentials:$", (String credentialType, DataTable dataTable) -> {
            world.setCredentials(dataTable.asMaps(String.class, String.class));

            if (credentialType.equals("incorrect")) world.expectsException(CredentialException.class);
            if (credentialType.equals("nonexistent")) world.expectsException(AccountNotFoundException.class);

            for (Map<String, String> credential : world.getCredentials()) {
                LoginRequest request = new LoginRequest()
                        .setEmail(credential.get("email"))
                        .setPassword(credential.get("password"));
                sessionHandler.received(world.createConnection(), request);
            }
        });
        Then("^they are authenticated$", () -> {
            for (JungleConnection connection : world.getConnections()) {
                try {
                    assertTrue(sessionService.isConnectionAuthorized(connection));
                    assertTrue(connection.isAuthorized());
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });
        Then("^they are not authenticated$", () -> {
            for (JungleConnection connection : world.getConnections()) {
                try {
                    assertFalse(sessionService.isConnectionAuthorized(connection));
                    assertFalse(connection.isAuthorized());
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });
        Then("^their account is locked$", () -> {
            for (Map<String, String> credential : world.getCredentials()) {
                try {
                    sessionService.isAccountLocked(credential.get("email"));
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });
        Then("^it fails$", () -> assertTrue(world.failed()));
        When("^they log out$", () -> {
            for (JungleConnection connection : world.getConnections()) {
                try {
                    sessionService.expireSession(connection.getAuthToken().getToken());
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });
        Then("^(?:their session is expired|they are logged out)$", () -> {
            for (JungleConnection connection : world.getConnections()) {
                try {
                    assertTrue(sessionService.isExpired(connection));
                } catch (Exception e) {
                    world.handleException(e);
                }
            }
        });

    }
}
