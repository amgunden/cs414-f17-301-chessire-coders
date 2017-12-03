package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;

public class InvitesModel extends Observable {

    private static InvitesModel instance = new InvitesModel();

    public static InvitesModel getInstance() {
        return instance;
    }

    private ObservableList<Invitation> pendingReceivedInvites;

    private InvitesModel() {
        pendingReceivedInvites = FXCollections.observableArrayList();
    }

    public ObservableList<Invitation> getPendingReceivedInvites() {
        return pendingReceivedInvites;
    }

    public void removeInvitation(Invitation invite) {
        pendingReceivedInvites.remove(invite);
    }

    public void addInvitation(Invitation invite) {
        pendingReceivedInvites.add(invite);
    }
}
