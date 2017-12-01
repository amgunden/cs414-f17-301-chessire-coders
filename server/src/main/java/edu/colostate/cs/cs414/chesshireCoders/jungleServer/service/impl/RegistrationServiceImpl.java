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

public class RegistrationServiceImpl implements RegistrationService {

    private DAOManager manager = DAOManager.instance(
            HikariConnectionProvider.getInstance(),
            PostgresDAOManager.class);

    @Override
    public void registerUser(final String nickName, final String email, final String hashedPassword) throws Exception {
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            UserDAO userDAO = manager.getUserDAO();
            LoginDAO loginDAO = manager.getLoginDAO();

            // check if there is an existing user with this nickname
            User user = userDAO.findByNickName(nickName);
            if (user == null) {
                user = new User().setNickName(nickName);
                userDAO.create(user);
            }
            // If the user row already exists, user may be re-registering.
            // If there is already existing login info, the DB will throw an error below.
            // Because this is an atomic transaction, all changes will be reverted on failure.
            else {
                user.setRegistered(true);
                userDAO.update(user);
            }

            Login login = new Login()
                    .setEmail(email)
                    .setHashedPass(hashedPassword)
                    .setNickName(user.getNickName());
            loginDAO.create(login);
            return null;
        });
    }

    @Override
    public void unregisterUser(String nickName) throws Exception {
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            UserDAO userDAO = manager.getUserDAO();
            LoginDAO loginDAO = manager.getLoginDAO();

            User user = userDAO
                    .findByPrimaryKey(nickName) // find user
                    .setRegistered(false); // mark as unregistered
            userDAO.update(user); // update DB

            loginDAO.delete(loginDAO.findByNickName(nickName)); // delete login info
            return null;
        });
    }

    @Override
    public User fetchUserByNickName(final String nickName) throws Exception {
        return manager.execute(manager -> manager.getUserDAO().findByNickName(nickName));
    }

    @Override
    public User fetchUserByEmail(final String email) throws Exception {
        return manager.execute(manager -> {
            Login login = manager.getLoginDAO()
                    .findByNickName(email);
            return manager.getUserDAO()
                    .findByPrimaryKey(login.getNickName());
        });
    }

    @Override
    public boolean isRegistered(final String email) throws Exception {
        return manager.execute(manager -> manager.getLoginDAO()
                .findByPrimaryKey(email) != null);
    }
}
