package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {  
		static Stage window;
	
	   @Override     
	   public void start(Stage primaryStage) throws Exception { 
		 window = primaryStage;
		 window.setTitle("Jungle");
		 
	     setScene("loginPage.fxml");
	   }
	   
	   public static void setScene(Scene scene)
	   {
		   window.setScene(scene);
		   window.show();
	   }
	   
	   public static void setScene(String fxmlFile) throws IOException
	   {
		   if (!fxmlFile.endsWith(".fxml"))
		   {
			   fxmlFile += ".fxml";
		   }
		   
		   Parent root = FXMLLoader.load(App.class.getResource("/fxml/" + fxmlFile));
		   Scene scene = new Scene(root);
		   window.setScene(scene);
		   window.show();
	   }
	   
	   public static void main(String args[]){           
	      launch(args);      
	   } 
	} 