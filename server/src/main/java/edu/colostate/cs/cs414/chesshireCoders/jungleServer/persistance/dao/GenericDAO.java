package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {

    /**
     * Add a new object to the table.
     *
     * @param newInstance The object to add
     * @return The primary key of the added object.
     * @throws SQLException
     */
    PK create(T newInstance) throws SQLException;

    /**
     * Selects a row from the table using the primary key column.
     *
     * @param pk primary key of row to select
     * @return Object corresponding to the selected row, or null on no result.
     * @throws SQLException
     */
    T findByPrimaryKey(PK pk) throws SQLException;

    /**
     * Returns all rows in the table.
     *
     * @return A list of all rows (be careful!)
     * @throws SQLException
     */
    List<T> findAll() throws SQLException;

    /**
     * Updates the row corresponding to the given object in the database.
     *
     * @param t object to update
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    int update(T t) throws SQLException;

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param pk Primary key
     * @return rows affected (should only be 1)
     * @throws SQLException
     */
    int delete(PK pk) throws SQLException;

    /**
     * Delete a row/rows from the database using an existing object for search criteria.
     * <p>
     * Implementations should build the DML statement to ignore NULL fields, and use only
     * non-NULL fields as criteria.
     *
     * @param t Search criteria object
     * @return rows affected.
     */
    int delete(T t) throws SQLException;
}
