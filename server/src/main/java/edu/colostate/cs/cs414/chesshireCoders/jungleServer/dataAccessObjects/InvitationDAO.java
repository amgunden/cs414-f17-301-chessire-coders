package edu.colostate.cs.cs414.chesshireCoders.jungleServer.dataAccessObjects;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.server.JungleDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for pushing/pulling Invitation data to/from the database.
 */
public class InvitationDAO extends AbstractDAO {


    public InvitationDAO(Connection connection) {
        super(connection);
    }

    /**
     * Retrieves an invitation from the database corresponding to the given ID.
     * @param id
     * @return
     * @throws SQLException
     */
    public Invitation getInvitation(int id) throws SQLException {
        String queryString = "SELECT * FROM public.\"GameInvitation\""
                + "WHERE \"GameInvitation\".\"InvitationID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, id);
            statement.executeQuery();

            try(ResultSet rs = statement.getResultSet()) {
                Invitation invitation = new Invitation();
                rs.next();

                invitation.setInvitationId(rs.getInt("InvitationID"));
                invitation.setSenderId(rs.getInt("Sender"));
                invitation.setRecipientId(rs.getInt("Recipient"));

                return invitation;
            }
        }
    }

    /**
     * Gets a list of invitations associated with a single sending user.
     *
     * @param senderId
     * @return
     * @throws SQLException
     */
    public List<Invitation> getInvitesBySender(int senderId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GameInvitation\""
                + "WHERE \"GameInvitation\".\"Sender\" = ?";
        try(PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, senderId);
            statement.executeQuery();

            try (ResultSet rs = statement.getResultSet()) {
                return constructListFromResultSet(rs);
            }
        }
    }

    /**
     * Gets a list of invitations associated with a single recipient user.
     * @param senderId
     * @param recipientId
     * @return
     * @throws SQLException
     */
    public List<Invitation> getInvitesBySenderRecipient(int senderId, int recipientId) throws SQLException {
        String queryString = "SELECT * FROM public.\"GameInvitation\""
                + "WHERE \"GameInvitation\".\"Sender\" = ?"
                + "AND \"GameInvitation\".\"Recipient\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            statement.setInt(1, senderId);
            statement.setInt(2, recipientId);
            statement.executeQuery();

            try (ResultSet rs = statement.getResultSet()) {
                return constructListFromResultSet(rs);
            }
        }
    }

    /**
     * Inserts a new Invitation object into the database.
     * @param invitation
     * @throws SQLException
     */
    public void insert(Invitation invitation) throws SQLException {
        String insertStr = "INSERT INTO public.\"GameInvitation\""
                + "(\"InvitationID\", \"Sender\", \"Recipient\")"
                + "VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertStr)) {
            statement.setInt(1, invitation.getInvitationId()); // TODO: remove this, invitation ID is auto generated
            statement.setInt(2, invitation.getSenderId());
            statement.setInt(3, invitation.getRecipientId());

            statement.executeUpdate();
        }
    }

    /**
     * Updates an existing invitation object in the database.
     *
     * @param invitation
     * @throws SQLException
     */
    public void update(Invitation invitation) throws SQLException {
        String insertStr = "UPDATE public.\"GameInvitation\" SET"
                + "\"Sender\" = ?,"
                + "\"Recipient\" = ?"
                + "WHERE \"InvitationID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(insertStr)) {
            statement.setInt(1, invitation.getSenderId());
            statement.setInt(2, invitation.getRecipientId());
            statement.setInt(3, invitation.getInvitationId());

            statement.executeUpdate();
        }
    }

    /**
     * Deletes an invitation from the database.
     *
     * @param invitation
     * @throws SQLException
     */
    public void delete(Invitation invitation) throws SQLException { // TODO: Change so that only ID is needed, or overload method.
        String deleteStr = "DELETE FROM public.\"GameInvitation\" WHERE \"InvitationID\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteStr)) {
            statement.setInt(1, invitation.getInvitationId());
            statement.executeUpdate();
        }
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
