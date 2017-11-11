package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.JungleConnection;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import helpers.ConnectionHelper;
import helpers.CredentialHelper;
import helpers.ExceptionHelper;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionSteps implements En {

    private SessionService sessionService = new SessionServiceImpl();
    private ExceptionHelper exceptionHelper = new ExceptionHelper();
    private ConnectionHelper connectionHelper = new ConnectionHelper();
    private CredentialHelper credentialHelper = new CredentialHelper();

    public SessionSteps() {

        When("^they log in with the following credentials:$", (DataTable dataTable) -> {

            List<List<String>> credentials = dataTable.asLists(String.class);

            for (List<String> credential : credentials) {
                credentialHelper.add(
                        credential.get(0),
                        credential.get(1),
                        credential.get(2));
                try {
                    JungleConnection connection = connectionHelper.newConnection();
                    sessionService.authenticate(
                            credential.get(0),
                            Crypto.hashSHA256(credential.get(1).getBytes()),
                            connection
                    );
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^they are authenticated$", () -> {
            for (JungleConnection connection : connectionHelper.getConnections()) {
                try {
                    assertTrue(sessionService.isAuthorized(connection));
                    assertTrue(connection.isAuthorized());
                } catch (SessionServiceImpl.InvalidConnectionException e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^they are not authenticated$", () -> {
            for (JungleConnection connection : connectionHelper.getConnections()) {
                try {
                    assertFalse(sessionService.isAuthorized(connection));
                    assertFalse(connection.isAuthorized());
                } catch (SessionServiceImpl.InvalidConnectionException e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^their account is locked$", () -> {
            for (String email : credentialHelper.getEmails()) {
                try {
                    sessionService.isAccountLocked(email);
                } catch (Exception e) {
                    exceptionHelper.handle(e);
                }
            }
        });
        Then("^it fails$", () -> assertFalse(exceptionHelper.failed()));
        Given("^the account does not exist$", () -> exceptionHelper.expectsException());
    }
}
