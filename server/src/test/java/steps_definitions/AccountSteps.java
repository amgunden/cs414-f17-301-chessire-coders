package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.SessionService;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.RegistrationServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.SessionServiceImpl;
import helpers.ExceptionHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AccountSteps implements En {

    ExceptionHelper exceptionHelper = new ExceptionHelper();
    RegistrationService registrationService = new RegistrationServiceImpl();

    public AccountSteps() {
        Given("^the following accounts exist:$", (DataTable dataTable) -> {
            for (Map<String,String> credential : dataTable.asMaps(String.class, String.class)) {
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
    }
}
