package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.LoginAttempt;

import java.sql.SQLException;
import java.util.Date;

public interface LoginAttemptDAO extends GenericDAO<LoginAttempt, Long> {

    int getUnsuccessfulAttemptsSince(Date date) throws SQLException;

    int getSuccessfulAttemptsSince(Date date) throws SQLException;
}
