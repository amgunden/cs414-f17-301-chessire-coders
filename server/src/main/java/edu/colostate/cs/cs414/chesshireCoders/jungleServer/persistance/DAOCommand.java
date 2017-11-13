package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

public interface DAOCommand<T> {
    T execute(final DAOManager manager) throws Exception;
}

