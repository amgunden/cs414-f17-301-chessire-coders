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
    public void registerUser(String nickName, String email, String hashedPassword) throws Exception {
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            UserDAO userDAO = manager.getUserDAO();
            LoginDAO loginDAO = manager.getLoginDAO();

            User user = userDAO.findByNickName(nickName);
            if (user == null) {
                user = new User()
                        .setNickName(nickName);
                user.setUserId(userDAO.create(user));
            }

            Login login = new Login()
                    .setEmail(email)
                    .setHashedPass(hashedPassword)
                    .setUserID(user.getUserId());
            loginDAO.create(login);
            return null;
        });
    }

    @Override
    public void unregisterUser(long userId) throws Exception {
        manager.executeAtomic((DAOCommand<Void>) manager -> {
            Login login = manager
                    .getLoginDAO()
                    .findByPrimaryKey(userId);
            User user = manager
                    .getUserDAO()
                    .findByPrimaryKey(userId)
                    .setRegistered(false);
            manager.getUserDAO()
                    .update(user);
            manager.getLoginDAO()
                    .delete(login);
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
					.findByEmail(email);
			return manager.getUserDAO()
					.findByPrimaryKey(login.getUserID());
		});
	}
		

    @Override
    public boolean isRegistered(final String email) throws Exception {
        return manager.execute(manager -> manager.getLoginDAO()
                .findByEmail(email) != null);
    }
}
