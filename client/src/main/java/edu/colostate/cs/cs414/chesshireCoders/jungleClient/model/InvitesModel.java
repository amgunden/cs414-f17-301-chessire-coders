package edu.colostate.cs.cs414.chesshireCoders.jungleClient.model;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Invitation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Observable;

public class InvitesModel extends Observable {

    private static InvitesModel instance = new InvitesModel();

    public static InvitesModel getInstance() {
        return instance;
    }

    private ObservableList<Invitation> pendingReceivedInvites;
    private ObservableList<String> availPlayers; // Will hold a list of players who can receive an invitation to a game

    private InvitesModel() {
        pendingReceivedInvites = FXCollections.observableArrayList();
        availPlayers = FXCollections.observableArrayList();
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

    public ObservableList<String> getAvailPlayers() {
        return availPlayers;
    }

    public void setAvailPlayers(List<String> availPlayers) {
        this.availPlayers.setAll(availPlayers);
    }

}
