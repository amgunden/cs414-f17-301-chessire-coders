package steps_definitions;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresLoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresUserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AccountSteps implements En {
    public AccountSteps() {
//        Given("^the following accounts exist:$", (DataTable dataTable) -> {
//            List<List<String>> accounts = dataTable.asLists(String.class);
//
//            for(List<String> account : accounts) {
//
//                try (Connection connection = HikariConnectionProvider.getInstance().getConnection()) {
//                    connection.setAutoCommit(false);
//                    try {
//                        PostgresUserDAO postgresUserDAO = new PostgresUserDAO(connection);
//                        PostgresLoginDAO postgresLoginDAO = new PostgresLoginDAO(connection);
//
//                        User user = new User();
//                        user.setNickName(account.get(1));
//                        int userId = postgresUserDAO.insert(user);
//
//                        Login login = new Login();
//                        login.setUserID(userId);
//                        login.setEmail(account.get(0));
//                        login.setHashedPass(Crypto.hashSHA256(account.get(2).getBytes()));
//
//                        postgresLoginDAO.insert(login);
//                    } catch (SQLException e) {
//                        connection.rollback();
//                        throw new RuntimeException(e);
//                    } finally {
//                        connection.setAutoCommit(true);
//                    }
//                } catch (SQLException | NoSuchAlgorithmException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
        throw new PendingException();
    }
}
