package edu.colostate.cs.cs414.chesshireCoders.jungleClient;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import javafx.application.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String args[]) {
        JungleClient client = null;

        if (args.length == 0) {
            System.out.println("Launching in client only mode...");
            Application.launch(App.class, args);
        } else {

            String propertiesFile = args[0];
            Properties properties;

            properties = new Properties();
            try {
                client = JungleClient.getInstance();
                Application.launch(App.class, args);

                FileInputStream in = new FileInputStream(propertiesFile);
                properties.load(in);

                String serverHostname = properties.getProperty("server-hostname", "localhost");
                int serverListenPort = Integer.parseInt(properties.getProperty("server-listenport", "9898"));

                client.setHostName(serverHostname);
                client.setListenPort(serverListenPort);
                client.setTimeout(5000);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
