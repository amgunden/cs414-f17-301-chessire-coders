package edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.postgres;

import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.RowMapper;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.BaseDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleServer.persistance.dao.InvitationDAO;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.types.InvitationStatusType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgresInvitationDAO extends BaseDAO<Invitation, Long> implements InvitationDAO {

    private RowMapper<Invitation> INVITATION_ROW_MAPPER = rs -> new Invitation()
    		.setInvitationId(rs.getLong("invitation_id"))
            .setSenderNickname(rs.getString("user_sender_nick_name"))
            .setRecipientNickName(rs.getString("user_recipient_nick_name"))
            .setGameId(rs.getLong("game_id"))
            .setInvitationStatus(InvitationStatusType.valueOf(rs.getString("invite_status")));

    PostgresInvitationDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Invitation> findBySenderNickName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM invitation WHERE user_sender_nick_name = ?";
        return query(sql, INVITATION_ROW_MAPPER, nickName);
    }

    @Override
    public List<Invitation> findByRecipientNIckName(String nickName) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM invitation WHERE user_recipient_nick_name = ?";
        return query(sql, INVITATION_ROW_MAPPER, nickName);
    }

    @Override
    public List<Invitation> findByRecipientNickName(String nickName, InvitationStatusType statusType) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM invitation WHERE user_recipient_nick_name = ? AND invite_status = ?";
        return query(sql, INVITATION_ROW_MAPPER, nickName, statusType.name());
    }

    @Override
    public List<Invitation> findByGameId(long gameId) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM invitation WHERE game_id = ?";
        return query(sql, INVITATION_ROW_MAPPER, gameId);
    }

    /**
     * Add a new object to the table.
     *
     * @param invitation The object to add
     * @return The primary key of the added object.
     */
    @Override
    public Long create(Invitation invitation) throws SQLException {
        //language=PostgreSQL
        String sql = "INSERT INTO invitation (user_sender_nick_name, user_recipient_nick_name, game_id, invite_status) VALUES (?,?,?,?)";
        return add(sql, Long.class,
                invitation.getSenderNickname(),
                invitation.getRecipientNickName(),
                invitation.getGameId(),
                invitation.getInvitationStatus().name());
    }

    /**
     * Selects a row from the table using the primary key column.
     *
     * @param invitationsId primary key of row to select
     * @return Object corresponding to the selected row, or null on no result.
     */
    @Override
    public Invitation findByPrimaryKey(Long invitationsId) throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM invitation WHERE invitation_id = ?";
        List<Invitation> invitations = query(sql, INVITATION_ROW_MAPPER, invitationsId);
        return invitations.isEmpty() ? null : invitations.get(0);
    }

    /**
     * Returns all rows in the table.
     *
     * @return A list of all rows (be careful!)
     */
    @Override
    public List<Invitation> findAll() throws SQLException {
        //language=PostgreSQL
        String sql = "SELECT * FROM invitation";
        return query(sql, INVITATION_ROW_MAPPER);
    }

    /**
     * Updates the row corresponding to the given object in the database.
     *
     * @param invitation object to update
     * @return rows affected (should only be 1)
     */
    @Override
    public int update(Invitation invitation) throws SQLException {
        //language=PostgreSQL
        String sql = "UPDATE invitation SET invite_status = ? WHERE invitation_id = ?";
        return modify(sql, invitation.getInvitationStatus(), invitation.getInvitationId());
    }

    /**
     * Deletes a single row from the database using the rows primary key.
     *
     * @param invitationId Primary key
     * @return rows affected (should only be 1)
     */
    @Override
    public int delete(Long invitationId) throws SQLException {
        //language=PostgreSQL
        String sql = "DELETE FROM invitation WHERE invitation_id = ?";
        return modify(sql, invitationId);
    }

    /**
     * Delete a row/rows from the database using an existing object for search criteria.
     * <p>
     * Implementations should build the DML statement to ignore NULL fields, and use only
     * non-NULL fields as criteria.
     *
     * @param invitation Search criteria object
     * @return rows affected.
     */
    @Override
    public int delete(Invitation invitation) {
        throw new UnsupportedOperationException("Currently unimplemented");
    }
}
