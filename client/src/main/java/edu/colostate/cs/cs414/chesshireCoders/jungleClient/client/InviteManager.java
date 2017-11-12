package edu.colostate.cs.cs414.chesshireCoders.jungleClient.client;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.game.JungleGame;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.events.InvitationEvent;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InviteManager {
	
	private static InviteManager instance;
	private ObservableList<Invitation> invitesReceived;
	
	private InviteManager() {
		invitesReceived = FXCollections.observableArrayList();
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
		return invitesReceived;
	}
	
	public void addInvitation(Invitation invite) {
		invitesReceived.add(invite);
	}
	
	public void addInvitationEvent(InvitationEvent invite) {
		// Add invitation to list
		// Call homecontroller to update list
	}
	
	public void removeInvitation(Invitation invite) {
		invitesReceived.remove(invite);
	}
	
	

}
