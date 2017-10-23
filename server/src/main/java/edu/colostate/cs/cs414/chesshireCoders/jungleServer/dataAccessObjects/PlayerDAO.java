package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.GameOutcomeType;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PlayerColor;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataObjects.Player;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private JungleDB jungleDB = null;
    private Connection connection = null;

    public PlayerDAO() {
        jungleDB = JungleDB.getInstance();
    }

    public void getConnection() throws SQLException {
        connection = jungleDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public List<Player> getPlayersByGameId(int gameId) throws SQLException {
        String queryString = "SELECT * FROM public.\"Player\""
                + "WHERE \"Player\".\"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, gameId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public List<Player> getPlayersByUserId(int userId) throws SQLException {
        String queryString = "SELECT * FROM public.\"Player\""
                + "WHERE \"Player\".\"UserID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, userId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public void insert(Player player) throws SQLException {
        String insertStr = "INSERT INTO public.\"Player\""
                + "(\"UserID\", \"PlayerColor\", \"Outcome\",\"GameID\")"
                + "VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setInt(1, player.getUserId());
        statement.setString(2, player.getColor().name().toLowerCase());
        statement.setString(3, player.getOutcomeType().name().toLowerCase());
        statement.setInt(4, player.getGameId());

        statement.executeUpdate();
    }

    public void update(Player player) throws SQLException {
        String insertStr = "UPDATE public.\"Player\" SET"
                + "\"Outcome\" = ?,"
                + "\"PlayerColor\" = ?"
                + "WHERE \"UserID\" = ? AND \"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setString(1, player.getOutcomeType().name().toLowerCase());
        statement.setString(2, player.getColor().name().toLowerCase());
        statement.setInt(3, player.getUserId());
        statement.setInt(4, player.getGameId());

        statement.executeUpdate();
    }

    public void delete(Player player) throws SQLException {
        String deleteStr = "DELETE FROM public.\"Player\" WHERE \"UserID\" = ? AND \"GameID\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setInt(1, player.getUserId());
        statement.setInt(1, player.getGameId());
        statement.executeUpdate();
    }

    private List<Player> constructListFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Player> players = new ArrayList<>();
        while (rs.next()) {
            Player player = new Player();
            player.setUserId(rs.getInt("UserID"));
            player.setColor(PlayerColor.valueOf(rs.getString("PlayerColor").toUpperCase()));
            player.setOutcomeType(GameOutcomeType.valueOf(rs.getString("Outcome")));
            players.add(player);
        }
        return players;
    }
}
