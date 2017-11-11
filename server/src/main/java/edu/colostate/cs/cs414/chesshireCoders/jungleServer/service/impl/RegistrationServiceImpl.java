package edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOCommand;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.DAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.HikariConnectionProvider;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.LoginDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.UserDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres.PostgresDAOManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.RegistrationService;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Login;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.sql.SQLException;

public class RegistrationServiceImpl implements RegistrationService {

    private DAOManager manager = DAOManager.instance(
            HikariConnectionProvider.getInstance(),
            PostgresDAOManager.class);

    @Override
    public void registerUser(String nickName, String email, String hashedPassword) throws SQLException {
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            UserDAO userDAO = manager.getUserDAO();
            LoginDAO loginDAO = manager.getLoginDAO();

            User user = new User()
                    .setNickName(nickName);
            long id = userDAO.create(user);

            Login login = new Login()
                    .setEmail(email)
                    .setHashedPass(hashedPassword)
                    .setUserID(id);
            loginDAO.create(login);
            return null;
        });
    }

    @Override
    public void unregisterUser(String email, AuthToken token) throws SQLException {
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            Login login = manager
                    .getLoginDAO()
                    .findByEmail(email);
            User user = manager
                    .getUserDAO()
                    .findByPrimaryKey(login.getUserID())
                    .setRegistered(false);
            manager.getUserDAO()
                    .update(user);
            manager.getLoginDAO()
                    .delete(login);
            return null;
        });
    }
}
