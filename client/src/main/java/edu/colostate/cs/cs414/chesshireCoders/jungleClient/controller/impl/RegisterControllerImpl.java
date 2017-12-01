package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.RegisterView;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;

public class RegisterControllerImpl extends BaseController<RegisterView> implements RegisterController {

    private final Listener listener = new Listener.ThreadedListener(new RegistrationListener());
    private final AccountModel accountModel = AccountModel.getInstance();
    private final JungleClient client = JungleClient.getInstance();

    public RegisterControllerImpl(RegisterView view) {
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
    public void sendRegistration(String email, String nickname, String hashedPassword) {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(email);
        request.setNickName(nickname);
        request.setPassword(hashedPassword);
        App.getJungleClient().sendMessage(request);
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
            view.registrationSuccess();
        } else view.registrationFailure(response.getErrMsg());
    }

    private class RegistrationListener extends Listener {
        @Override
        public void received(Connection connection, Object received) {
            if (received instanceof RegisterResponse) {
                handleRegisterResponse((RegisterResponse) received);
            }
        }
    }
}
