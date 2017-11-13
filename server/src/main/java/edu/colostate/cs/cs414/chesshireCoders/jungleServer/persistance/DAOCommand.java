package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.impl.GameServiceImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.service.util.GameStateException;

public interface DAOCommand<T> {
    T execute(final DAOManager manager) throws Exception, GameStateException, GameStateException;
}

