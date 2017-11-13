package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.*;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOManager {

    /**
     * This ensures only one instance of DAOFactory per thread. This ensures by extension
     * that we only have one connection per thread, and per DAO on that thread. This allows
     * the data layer to handle connections, rather than having the service or handler layers
     * worry about it.
     */
    private static ThreadLocal<DAOManager> INSTANCE = ThreadLocal.withInitial(() -> null);

    /**
     * Get's an instance of DAOManager for this thread.
     *
     * @param provider
     * @param concreteDAOManager
     * @return
     */
    public static DAOManager instance(ConnectionProvider provider, Class concreteDAOManager) {
        return INSTANCE.get() == null ? create(provider, concreteDAOManager) : INSTANCE.get();
    }

    /**
     * Create a new DAOManager instance.
     *
     * @param provider           The connection provider to pass to the concrete manager.
     * @param concreteDAOManager The concrete manager type.
     * @return Returns an instantiated DAOManager.
     */
    private static DAOManager create(ConnectionProvider provider, Class concreteDAOManager) {
        try {
            INSTANCE.set((DAOManager) concreteDAOManager.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not instantiate DAO manager class");
        }
        INSTANCE.get().setProvider(provider);
        return INSTANCE.get();
    }

    private ConnectionProvider provider;
    private Connection connection;
    private boolean autoCommit = true;

    public void setProvider(ConnectionProvider provider) {
        this.provider = provider;
    }

    /**
     * Obtains a connection and sets AutoCommit according the the autoCommit property
     *
     * @return java.sql.Connection
     * @throws SQLException on failure to obtain connection.
     */
    protected Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed())
            this.connection = provider.getConnection();
        this.connection.setAutoCommit(autoCommit);
        return connection;
    }

    /**
     * Execute a DAOCommand where each DAO operation is its own transaction.
     *
     * @param command The command to execute
     * @param <T>     The return type of the command
     * @throws SQLException on failure to obtain connection.
     */
    public <T> T execute(DAOCommand<T> command) throws Exception {
        this.autoCommit = true;
        try {
            return command.execute(this);
        } finally {
            getConnection().close();
        }
    }

    /**
     * Execute a DAOCommand where the entire command is executed as one Atomic transaction.
     *
     * @param command The command to execute
     * @param <T>     The return type of the command
     * @throws SQLException on failure to obtain connection.
     */
    public <T> T executeAtomic(DAOCommand<T> command) throws Exception {
        this.autoCommit = false;
        try {
            T t = command.execute(this);
            getConnection().commit();
            return t;
        } catch (Exception e) {
            getConnection().rollback();
            throw e;
        } finally {
            getConnection().setAutoCommit(true);
            getConnection().close();
        }
    }

    public abstract LoginDAO getLoginDAO() throws SQLException;

    public abstract UserDAO getUserDAO() throws SQLException;

    public abstract LoginAttemptDAO getLoginAttemptDAO() throws SQLException;

    public abstract UserSessionDAO getUserSessionDAO() throws SQLException;

    public abstract GameDAO getGameDAO() throws SQLException;

    public abstract GamePieceDAO getGamePieceDAO() throws SQLException;

    public abstract InvitationDAO getInvitationDAO() throws SQLException;
}
