package edu.colostate.cs.cs414.chesshireCoders.jungleServer.data;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<GamePiece> getPiecesByGameID(int gameId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GamePiece\" WHERE \"GamePiece\".\"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, gameId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public List<GamePiece> getPiecesByPlayerID(int playerId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GamePiece\" WHERE \"GamePiece\".\"PlayerID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, playerId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

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

    public void delete(GamePiece piece) throws SQLException {
        String deleteStr = "DELETE FROM public.\"GamePiece\" WHERE \"PieceID\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setInt(1, piece.getPieceId());
        statement.executeUpdate();
    }

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
