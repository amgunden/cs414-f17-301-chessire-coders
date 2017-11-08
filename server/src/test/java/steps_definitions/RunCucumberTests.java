package steps_definitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import helpers.DatabaseHelper;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.sql.SQLException;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber"},
        features = {"src/test/resources/features"},
        glue = {"steps_definitions"}
)
public class RunCucumberTests {

    /**
     * This should only be run once, before all cucumber tests.
     *
     * @throws SQLException
     */
    @BeforeClass
    public static void prepareDatabase() throws SQLException {
        DatabaseHelper.initializeDatabase();
    }
}
