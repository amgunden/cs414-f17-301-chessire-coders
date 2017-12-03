package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;

import java.io.IOException;

public class RegisterControllerImpl extends BaseController implements RegisterController {

    private final Listener listener = new Listener.ThreadedListener(new RegistrationListener());
    private final AccountModel accountModel = AccountModel.getInstance();
    private final JungleClient client = JungleClient.getInstance();

    public RegisterControllerImpl(View view) {
        super(view);
        client.addListener(listener);
    }

    @Override
    public void dispose() {
        client.removeListener(listener);
    }

    /**
     * Sends a registration request to the server.
     */
    @Override
    public void sendRegistration(String email, String nickname, String hashedPassword) throws IOException {
        // have to reset the BooleanProperty to null because it will only fire a notification on a change in value.
        accountModel.setLoginSuccess(null);
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setNickName(nickname);
        request.setPassword(hashedPassword);
        client.sendMessage(request);
    }

    /**
     * Handle a response from the server for registration
     */
    private void handleRegisterResponse(RegisterResponse response) {
        accountModel.setLoginSuccess(response.isSuccess());
        if (response.isSuccess()) {
            accountModel.setLoginSuccess(response.isSuccess());
            accountModel.setNickName(response.getNickName());
            accountModel.setAuthToken(response.getAuthToken());
        } else view.showError(response.getErrMsg());
    }

    private class RegistrationListener extends Listener {
        @Override
        public void received(Connection connection, Object received) {
            try {
                if (received instanceof RegisterResponse) {
                    handleRegisterResponse((RegisterResponse) received);
                }
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }
    }
}
