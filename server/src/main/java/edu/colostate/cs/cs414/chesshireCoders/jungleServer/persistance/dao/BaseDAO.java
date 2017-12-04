package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.GameStatus;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

public abstract class BaseDAO<T, PK extends Serializable> {

    private Connection connection;

    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    protected PK add(final String sql, Class<PK> tClass, final Object... params)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(statement, params);
            final int result = statement.executeUpdate();
            PK id = null;
            if (result != 0) {
                ResultSet set = statement.getGeneratedKeys();
                if (set.next()) {
                    id = set.getObject(1, tClass);
                }
            }
            if (id == null)
                throw new SQLException("Failed to get ID on add.");
            return id;
        }
    }

    protected List<T> query(final String sql, final RowMapper<T> mapper, final Object... params)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            ResultSet set = statement.executeQuery();
            final List<T> list = new ArrayList<>();
            while (set.next()) {
                list.add(mapper.map(set));
            }
            return list;
        }
    }

    protected List<T> query(final String sql, final RowMapper<T> mapper)
            throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet set = statement.executeQuery();
            final List<T> list = new ArrayList<>();
            while (set.next()) {
                list.add(mapper.map(set));
            }
            return list;
        }
    }

    protected int modify(final String sql, final Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            return statement.executeUpdate();
        }
    }

    public int count(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            ResultSet set = statement.executeQuery();
            if (set.next())
                return set.getInt("count");
            else return -1;
        }
    }

    private void setParameters(final PreparedStatement statement, final Object... parameters) throws SQLException {
        for (int i = 0, length = parameters.length; i < length; i++) {
            final Object parameter = parameters[i];
            final int parameterIndex = i + 1;
            if (parameter == null) {
                statement.setObject(parameterIndex, null);
            } else if (parameter instanceof Boolean) {
                statement.setBoolean(parameterIndex, (Boolean) parameter);
            } else if (parameter instanceof Character) {
                statement.setString(parameterIndex, String.valueOf(parameter));
            } else if (parameter instanceof Byte) {
                statement.setByte(parameterIndex, (Byte) parameter);
            } else if (parameter instanceof Short) {
                statement.setShort(parameterIndex, (Short) parameter);
            } else if (parameter instanceof Integer) {
                statement.setInt(parameterIndex, (Integer) parameter);
            } else if (parameter instanceof Long) {
                statement.setLong(parameterIndex, (Long) parameter);
            } else if (parameter instanceof Float) {
                statement.setFloat(parameterIndex, (Float) parameter);
            } else if (parameter instanceof Double) {
                statement.setDouble(parameterIndex, (Double) parameter);
            } else if (parameter instanceof String) {
                statement.setString(parameterIndex, (String) parameter);
            } else if (parameter instanceof Date) {
                statement.setTimestamp(parameterIndex, new java.sql.Timestamp(((Date) parameter).getTime()));
            } else if (parameter instanceof Calendar) {
                statement.setDate(parameterIndex, new java.sql.Date(((Calendar) parameter)
                        .getTimeInMillis()));
            } else if (parameter instanceof BigDecimal) {
                statement.setBigDecimal(parameterIndex, (BigDecimal) parameter);
            } else if (parameter instanceof InvitationStatusType) {
                statement.setString(parameterIndex, ((InvitationStatusType) parameter).name());
            } else if (parameter instanceof GameStatus) {
                statement.setString(parameterIndex, ((GameStatus) parameter).name());
            } else if (parameter instanceof GameStatus[]) {
            	GameStatus[] statusArray = (GameStatus[]) parameter;
            	for (int j = 0; j < statusArray.length; j++) {
                    statement.setString(parameterIndex+j, (statusArray[i]).name());
				}
            	i += statusArray.length - 1;
            } else {
                throw new IllegalArgumentException(String.format(
                        "Unknown type of the parameter is found. [param: %s, paramIndex: %s]",
                        parameter,
                        parameterIndex));
            }
        }
    }

    protected Connection getConnection() {
        return connection;
    }
}
