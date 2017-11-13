package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.InviteReplyRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InviteManager {
	
	private static InviteManager instance;
	private ObservableList<Invitation> invites;
	
	private InviteManager() {
		invites = FXCollections.observableArrayList();
	}
	
	public static InviteManager getInstance()
	{
		if (instance == null) {
			instance = new InviteManager();
		}
		
		return instance;
	}
	
	public ObservableList<Invitation> getInvites()
	{
		return invites;
	}
	
	public void addInvitation(Invitation invite) {
		invites.add(invite);
	}
	
	public void addInvitationEvent(InvitationEvent inviteEvent) {
		if (inviteEvent != null && inviteEvent.getInvite() != null)
			addInvitation(inviteEvent.getInvite());
	}
	
	public void removeInvitation(Invitation invite) {
		invites.remove(invite);
	}
	
	public void acceptInvite(Invitation invite) {
		InviteReplyRequest request = new InviteReplyRequest(AuthTokenManager.getInstance().getToken(), true, invite.getInvitationId());
		App.getJungleClient().sendMessage(request);
	}
	
	public void declineInvite(Invitation invite) {
		InviteReplyRequest request = new InviteReplyRequest(AuthTokenManager.getInstance().getToken(), false, invite.getInvitationId());
		App.getJungleClient().sendMessage(request);
	}

}
