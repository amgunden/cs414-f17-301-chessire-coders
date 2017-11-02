package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;

public class RegistrationHandler extends Listener {

    private RegisterController registerController;

    public RegistrationHandler(RegisterController controller) {
        this.registerController = controller;
    }

    public void sendRegistration() {
        registerController.registrationSuccess();
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof RegisterResponse) {
            handleRegisterResponse((RegisterResponse) received);
        } else if (received instanceof UnRegisterResponse) {

        }
    }

    void handleRegisterResponse(RegisterResponse response) {
        if (response.isSuccess()) { // I'll add some getters and setters in my PR, currently everything is package private
//            setRegisterUserStatus(true);
            registerController.registrationSuccess();
        } else {
            registerController.registrationFailure();
        }
    }
}
