import com.esotericsoftware.kryonet.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JungleServer {

    private Properties settings;
    private Server server;

    public JungleServer(String defaultConfigPath, String configPath) {


        try {
            Properties defaultSettings = new Properties();
            FileInputStream in = new FileInputStream(defaultConfigPath);
            defaultSettings.load(in);

            settings = new Properties(defaultSettings);

            in = new FileInputStream(configPath);
            settings.load(in);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            server = new Server();
            server.start();
            server.bind();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int main(String[] args) {
        return 0;
    }
}
