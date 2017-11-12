package steps_definitions;

import cucumber.api.java8.En;
import helpers.DatabaseHelper;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class DatabaseHooks implements En {

    public DatabaseHooks() {
        String[] tags = {"@DBClean"};
        Before(tags, DatabaseHelper::clean);
    }
}
