package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class HomeController implements Initializable {

	@FXML
	private ImageView btnPlay;
	
	@FXML
	private ImageView btnViewProfile;
	
	@FXML
	private ImageView btnViewInvites;
	
	public void playClicked()
	{
		System.out.println("Play Clicked.");
	}
	
	public void viewProfileClicked()
	{
		System.out.println("View Profile Clicked.");
	}
	
	public void viewInvitesClicked()
	{
		System.out.println("View Invites Clicked.");
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
