package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.GamePiece;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for pushing/pulling data about GamePiece's to/from the database.
 */
public class GamePieceDAO {

    private JungleDB jungleDB = null;
    private Connection connection = null;

    public GamePieceDAO() {
        jungleDB = JungleDB.getInstance();
    }

    public void getConnection() throws SQLException {
        connection = jungleDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Retrieves the information about the game piece with an ID of 'id'
     *
     * @param id The ID of the game piece to retrieve.
     * @return An instance of GamePiece
     * @throws SQLException
     */
    public GamePiece getGamePiece(int id) throws SQLException {

        String queryString = "SELECT * FROM public.\"GamePiece\" WHERE \"GamePiece\".\"PieceID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, id);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        GamePiece piece = new GamePiece();
        rs.next();

        piece.setPieceId(rs.getInt("PieceID"));
        piece.setPlayerId(rs.getInt("PlayerID"));
        piece.setPieceType(PieceType.valueOf(rs.getString("Piecetype").toUpperCase()));
        piece.setColumn(rs.getInt("CoordY"));
        piece.setRow(rs.getInt("CoordX"));
        piece.setGameId(rs.getInt("GameID"));

        return piece;
    }

    /**
     * Retrieves the list of GamePieces associated with a particular game.
     *
     * @param gameId The gameID
     * @return Returns a list of GamePieces
     * @throws SQLException
     */
    public List<GamePiece> getPiecesByGameID(int gameId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GamePiece\" WHERE \"GamePiece\".\"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, gameId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    /**
     * Retrieves a list of GamePiece's associated with a particular player ID.
     *
     * @param playerId
     * @return
     * @throws SQLException
     */
    public List<GamePiece> getPiecesByPlayerID(int playerId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GamePiece\" WHERE \"GamePiece\".\"PlayerID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, playerId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    /**
     * Returns the list of GamePiece's associated with both a given playerID and gameID
     *
     * @param playerId
     * @param gameId
     * @return
     * @throws SQLException
     */
    public List<GamePiece> getPiecesByPlayerGameId(int playerId, int gameId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GamePiece\""
                + "WHERE \"GamePiece\".\"PlayerID\" = ?"
                + "AND \"GamePiece\".\"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, playerId);
        statement.setInt(2, gameId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    /**
     * Inserts a new GamePiece into the Database.
     *
     * @param piece
     * @throws SQLException
     */
    public void insert(GamePiece piece) throws SQLException {
        String insertStr = "INSERT INTO public.\"GamePiece\""
                + "(\"PieceID\", \"PlayerID\", \"PieceType\", \"CoordY\", \"CoordX\", \"GameID\")"
                + "VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setInt(1, piece.getPieceId());
        statement.setInt(2, piece.getPlayerId());
        statement.setString(3, piece.getPieceType().name().toLowerCase());
        statement.setInt(4, piece.getColumn());
        statement.setInt(5, piece.getRow());
        statement.setInt(6, piece.getGameId());

        statement.executeUpdate();
    }

    /**
     * Updates an existing GamePiece row in the database.
     *
     * @param piece
     * @throws SQLException
     */
    public void update(GamePiece piece) throws SQLException {
        String insertStr = "UPDATE public.\"GamePiece\" SET"
                + "\"PlayerID\" = ?,"
                + "\"PieceType\" = ?,"
                + "\"CoordY\" = ?,"
                + "\"CoordX\" = ?,"
                + "\"GameID\" = ?"
                + "WHERE \"PieceID\" = ?";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setInt(1, piece.getPlayerId());
        statement.setString(2, piece.getPieceType().name().toLowerCase());
        statement.setInt(3, piece.getColumn());
        statement.setInt(4, piece.getRow());
        statement.setInt(5, piece.getGameId());
        statement.setInt(6, piece.getPieceId());

        statement.executeUpdate();
    }

    /**
     * Deletes a GamePiece row from the database.
     *
     * @param piece
     * @throws SQLException
     */
    public void delete(GamePiece piece) throws SQLException {
        String deleteStr = "DELETE FROM public.\"GamePiece\" WHERE \"PieceID\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setInt(1, piece.getPieceId());
        statement.executeUpdate();
    }

    /**
     * utility method to generate a list of GamePieces from a ResultSet.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<GamePiece> constructListFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<GamePiece> pieces = new ArrayList<>();
        while (rs.next()) {
            GamePiece piece = new GamePiece();
            piece.setPieceId(rs.getInt("PieceID"));
            piece.setPlayerId(rs.getInt("PlayerID"));
            piece.setPieceType(PieceType.valueOf(rs.getString("Piecetype").toUpperCase()));
            piece.setColumn(rs.getInt("CoordY"));
            piece.setRow(rs.getInt("CoordX"));
            piece.setGameId(rs.getInt("GameID"));
            pieces.add(piece);
        }
        return pieces;
    }
}
