package steps_definitions.hooks;

import cucumber.api.java8.En;
import helpers.DatabaseHelper;
import main.World;

public class DatabaseHooks implements En {

    public DatabaseHooks(World world) {
        String[] tags = {"@DBClean"};
        Before(tags, DatabaseHelper::clean);
    }
}
