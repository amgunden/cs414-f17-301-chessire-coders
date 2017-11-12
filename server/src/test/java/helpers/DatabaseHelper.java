package helpers;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHelper {

    private static final String scriptFile = "./src/test/resources/h2/schema.sql";
    private static final String jdbcUrl = "jdbc:h2:file:./src/test/resources/h2/test.db;AUTO_SERVER=TRUE;DATABASE_TO_UPPER=false;DB_CLOSE_ON_EXIT=FALSE";
    private static final String dbUser = "jungle";
    private static final String dbPassword = "jungle";
    private static final String dbDataSource = "org.h2.jdbcx.JdbcDataSource";

    public static void initializeDatabase() throws SQLException, FileNotFoundException {
        Properties dataSourceProperties = new Properties();

        dataSourceProperties.setProperty("dataSourceClassName", dbDataSource);
        dataSourceProperties.setProperty("dataSource.url", jdbcUrl);
        dataSourceProperties.setProperty("dataSource.user", dbUser);
        dataSourceProperties.setProperty("dataSource.password", dbPassword);

        HikariConnectionProvider.initialize(dataSourceProperties);
    }

    public static void clean() {
        //language=H2
        String sql = "RUNSCRIPT FROM './src/test/resources/h2/schema.sql'";
        try (Connection connection = HikariConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void tearDown() {
        //language=H2
        String sql = "DROP ALL OBJECTS DELETE FILES; SHUTDOWN;";
        try (Connection connection = HikariConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.execute();
            }
        } catch (SQLException ignored) {
        } finally {
            DeleteDbFiles.execute("./src/test/resources/h2/", "test.db", true);
        }
    }
}
