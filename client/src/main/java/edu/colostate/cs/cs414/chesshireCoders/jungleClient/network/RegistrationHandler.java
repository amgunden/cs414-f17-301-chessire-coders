package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import javafx.application.Platform;

public class RegistrationHandler extends Listener {

    private RegisterController registerController;

    public RegistrationHandler(RegisterController controller) {
        this.registerController = controller;
    }

    public void sendRegistration(String email, String nickname, String hashedPassword) {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setNickName(nickname);
        request.setPassword(hashedPassword);
        App.getJungleClient().sendMessage(request);
    }

    public void sendUnregisterRequest() {
        UnRegisterRequest request = new UnRegisterRequest();
        request.setAuthToken(AuthTokenManager
                .getInstance()
                .getToken());
    }

    @Override
    public void received(Connection connection, Object received) {
        super.received(connection, received);
        if (received instanceof RegisterResponse) {
            handleRegisterResponse((RegisterResponse) received);
        }
    }

    void handleRegisterResponse(RegisterResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(() -> {
            if (response.isSuccess()) {
                AuthTokenManager.getInstance().setAuthToken(response.getAuthToken());
                registerController.registrationSuccess();
            } else {
                registerController.registrationFailure(response.getErrMsg());
            }
        });
    }
}
