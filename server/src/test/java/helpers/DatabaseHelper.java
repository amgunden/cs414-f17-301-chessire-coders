package helpers;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.*;
import java.util.Properties;

public class DatabaseHelper {

    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/jungle";
    private static final String dbUser = "jungle";
    private static final String dbPassword = "7Dh@Z5efPChIpb24o&";
    private static final String dbDataSource = "org.postgresql.ds.PGSimpleDataSource";

    public static void initializeDatabase() {
        Properties dataSourceProperties = new Properties();

        dataSourceProperties.setProperty("dataSourceClassName", dbDataSource);
        dataSourceProperties.setProperty("dataSource.url", jdbcUrl);
        dataSourceProperties.setProperty("dataSource.user", dbUser);
        dataSourceProperties.setProperty("dataSource.password", dbPassword);

        JungleDB.initialize(dataSourceProperties);
    }

    public static void cleanDatabase() throws SQLException {
        //language=PostgreSQL
        String sql = "DROP SCHEMA public CASCADE;\n" +
                "CREATE SCHEMA public;";
        executeUpdate(sql);
    }

    public static void setupDatabase() throws SQLException {
        createUserTable();
        createLoginTable();
        createLoginAttemptTable();
    }

    private static void createUserTable() throws SQLException {
        //language=PostgreSQL
        String sql = "CREATE TABLE \"User\"\n" +
                "(\n" +
                "  \"UserID\"    BIGSERIAL   NOT NULL,\n" +
                "  \"NameFirst\" VARCHAR(20),\n" +
                "  \"NameLast\"  VARCHAR(20),\n" +
                "  \"NickName\"  VARCHAR(30) NOT NULL,\n" +
                "\n" +
                "  CONSTRAINT \"PK_User_UserID\" PRIMARY KEY (\"UserID\"),\n" +
                "  CONSTRAINT \"UNQ_User_NickName\" UNIQUE (\"NickName\")\n" +
                ");\n";
        executeUpdate(sql);
    }

    private static void createLoginTable() throws SQLException {
        //language=PostgreSQL
        String sql = "CREATE TABLE \"Login\"\n" +
                "(\n" +
                "  \"UserID\"     INTEGER     NOT NULL,\n" +
                "  \"Email\"      VARCHAR(64) NOT NULL,\n" +
                "  \"HashedPass\" CHAR(44)    NOT NULL,\n" +
                "  CONSTRAINT \"PK_Login_UserID\" PRIMARY KEY (\"UserID\"),\n" +
                "  CONSTRAINT \"UNQ_Login_Email\" UNIQUE (\"Email\"),\n" +
                "  CONSTRAINT \"FK_UserID_User\" FOREIGN KEY (\"UserID\") REFERENCES \"User\" (\"UserID\")\n" +
                ");\n";
        executeUpdate(sql);
    }

    private static void createLoginAttemptTable() throws SQLException {
        //language=PostgreSQL
        String sql = "CREATE TABLE \"LoginAttempt\"\n" +
                "(\n" +
                "  \"UserID\" INT       NOT NULL,\n" +
                "  \"Time\"   TIMESTAMP NOT NULL,\n" +
                "  \"Locked\" BOOLEAN   NOT NULL DEFAULT FALSE,\n" +
                "\n" +
                "  CONSTRAINT \"FK_LoginID_Login\" FOREIGN KEY (\"UserID\") REFERENCES \"Login\" (\"UserID\")\n" +
                ");";
        executeUpdate(sql);
    }

    public static ResultSet executeUpdate(String sql) throws SQLException {
        try (Connection connection = JungleDB.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                return statement.getResultSet();
            }
        }
    }

    public static ResultSet executeQuery(String sql) throws SQLException {
        try (Connection connection = JungleDB.getInstance().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                return statement.getResultSet();
            }
        }
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + resultSetMetaData.getColumnName(i));
                }
                System.out.println("");
            }
        } finally {
            resultSet.close();
        }
    }
}
