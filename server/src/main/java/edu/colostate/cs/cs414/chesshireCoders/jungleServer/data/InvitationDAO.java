package edu.colostate.cs.cs414.chesshireCoders.jungleServer.data;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PieceType;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvitationDAO {

    private JungleDB jungleDB = null;
    private Connection connection = null;

    public InvitationDAO() {
        jungleDB = JungleDB.getInstance();
    }

    public void getConnection() throws SQLException {
        connection = jungleDB.getConnection();
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public Invitation getInvitation(int id) throws SQLException {
        String queryString = "SELECT * FROM public.\"GameInvitation\""
                + "WHERE \"GameInvitation\".\"InvitationID\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, id);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        Invitation invitation = new Invitation();
        rs.next();

        invitation.setInvitationId(rs.getInt("InvitationID"));
        invitation.setSenderId(rs.getInt("Sender"));
        invitation.setRecipientId(rs.getInt("Recipient"));

        return invitation;
    }

    public List<Invitation> getInvitesBySender(int senderId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GameInvitation\""
                + "WHERE \"GameInvitation\".\"Sender\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, senderId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public List<Invitation> getInvitesBySenderRecipient(int senderId, int recipientId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GameInvitation\""
                + "WHERE \"GameInvitation\".\"Sender\" = ?"
                + "AND \"GameInvitation\".\"Recipient\" = ?";
        PreparedStatement statement = connection.prepareStatement(queryString);
        statement.setInt(1, senderId);
        statement.setInt(2, recipientId);
        statement.executeQuery();

        ResultSet rs = statement.getResultSet();
        return constructListFromResultSet(rs);
    }

    public void insert(Invitation invitation) throws SQLException {
        String insertStr = "INSERT INTO public.\"GameInvitation\""
                + "(\"InvitationID\", \"Sender\", \"Recipient\")"
                + "VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setInt(1, invitation.getInvitationId());
        statement.setInt(2, invitation.getSenderId());
        statement.setInt(3, invitation.getRecipientId());

        statement.executeUpdate();
    }

    public void update(Invitation invitation) throws SQLException {
        String insertStr = "UPDATE public.\"GameInvitation\" SET"
                + "\"Sender\" = ?,"
                + "\"Recipient\" = ?"
                + "WHERE \"InvitationID\" = ?";
        PreparedStatement statement = connection.prepareStatement(insertStr);
        statement.setInt(1, invitation.getSenderId());
        statement.setInt(2, invitation.getRecipientId());
        statement.setInt(3, invitation.getInvitationId());

        statement.executeUpdate();
    }

    public void delete(Invitation invitation) throws SQLException {
        String deleteStr = "DELETE FROM public.\"GameInvitation\" WHERE \"InvitationID\" = ?";
        PreparedStatement statement = connection.prepareStatement(deleteStr);
        statement.setInt(1, invitation.getInvitationId());
        statement.executeUpdate();
    }

    private List<Invitation> constructListFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Invitation> invitations = new ArrayList<>();
        while(rs.next()) {
            Invitation invitation = new Invitation();
            invitation.setInvitationId(rs.getInt("InvitationID"));
            invitation.setSenderId(rs.getInt("Sender"));
            invitation.setRecipientId(rs.getInt("Recipient"));
            invitations.add(invitation);
        }
        return invitations;
    }
}
