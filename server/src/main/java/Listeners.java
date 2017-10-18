import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Listeners extends Listener {

    private JungleServer server;

    public Listeners(JungleServer server) {
        this.server = server;
    }

    public void connected(Connection connection) {
    }

    public void disconnected(Connection connection) {
    }

    public void received(Connection connection, Object object) {
    }

    public void idle(Connection connection) {
    }
}
