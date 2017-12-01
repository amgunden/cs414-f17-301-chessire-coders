package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    static Stage window;

    private static JungleClient client;
    
    public static JungleClient getJungleClient() {
    	return client;
    }

    public static Stage getWindow() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Jungle");

        setScene("loginPage.fxml");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
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
}
