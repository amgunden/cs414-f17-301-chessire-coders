package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {  
	   @Override     
	   public void start(Stage primaryStage) throws Exception { 
	     
	     //Code for JavaFX application. 
	     //(Stage, scene, scene graph)      
		 //creating a Group object 
	     Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
	       
	     //Creating a Scene by passing the group object, height and width   
	     Scene scene = new Scene(root);
		   
		 //Setting the title to Stage. 
		  primaryStage.setTitle("Jungle"); 
		          
		 //Setting the scene to Stage 
		 primaryStage.setScene(scene); 
		          
		 //Displaying the stage 
		 primaryStage.show();
	       
	   }         
	   public static void main(String args[]){           
	      launch(args);      
	   } 
	} 