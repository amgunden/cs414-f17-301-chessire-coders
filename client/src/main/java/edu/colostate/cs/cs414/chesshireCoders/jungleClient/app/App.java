package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.account.AccountHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.JungleClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class App extends Application {
    static Stage window;
    
    static JungleClient client;
    
    public static JungleClient getJungleClient() {
    	return client;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Jungle");

        setScene("loginPage.fxml");
    }

    public static void setScene(Scene scene) {
        window.setScene(scene);
        window.show();
    }

    public static void setScene(String fxmlFile) throws IOException {
        if (!fxmlFile.endsWith(".fxml")) {
            fxmlFile += ".fxml";
        }

        Parent root = FXMLLoader.load(App.class.getResource("/fxml/" + fxmlFile));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    public static void main(String args[]) {
        if (args.length == 0)
    	{
    		System.out.println("Launching in client only mode...");
    		launch(args);
    	} else {
    	
	        String propertiesFile = args[0];
	        Properties properties;
	        JungleClient client = null;
	
	        properties = new Properties();
	        try {
	            FileInputStream in = new FileInputStream(propertiesFile);
	            properties.load(in);
	
	            String serverHostname = properties.getProperty("server-hostname", "localhost");
	            int serverListenPort = Integer.parseInt(properties.getProperty("server-listenport", "9898"));
	
	            client = new JungleClient();
	            client.start();
	            client.connect(5000, serverHostname, serverListenPort);
	            

	            launch(args);
	
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (client != null) {
	                client.stop();
	            }
	        }
    	}
    }
}
