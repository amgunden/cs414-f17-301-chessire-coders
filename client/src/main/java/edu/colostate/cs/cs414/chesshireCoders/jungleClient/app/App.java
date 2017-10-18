package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.ui.LoginScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {  
	   @Override     
	   public void start(Stage primaryStage) throws Exception { 
	     
	     //Code for JavaFX application. 
	     //(Stage, scene, scene graph)      
		 //creating a Group object 
	     StackPane pane = new StackPane(); 
	       
	     //Creating a Scene by passing the group object, height and width   
	     Scene scene = new LoginScene(pane ,600, 500);
		   
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