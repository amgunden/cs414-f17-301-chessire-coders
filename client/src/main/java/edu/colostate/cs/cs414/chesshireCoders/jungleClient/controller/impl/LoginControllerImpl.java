package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.BaseController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.model.AccountModel;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;

import java.io.IOException;

public class LoginControllerImpl extends BaseController implements LoginController {

    private final Listener loginListener = new Listener.ThreadedListener(new LoginListener());
    private JungleClient client = JungleClient.getInstance();
    private AccountModel accountModel = AccountModel.getInstance();

    public LoginControllerImpl(View view) {
        super(view);
        client.addListener(loginListener);
    }

    @Override
    public void dispose() {
        client.removeListener(loginListener);
    }

    @Override
    public void sendLogin(String email, String hashedPassword) throws IOException {
        accountModel.setLoginSuccess(null);
        client.sendMessage(new LoginRequest(email, hashedPassword));
    }

    /**
     * Handle a response from the server for login
     */
    private void handleLoginResponse(LoginResponse response) {
        accountModel.setLoginSuccess(response.isSuccess());
        if (response.isSuccess()) {
            accountModel.setLoginSuccess(response.isSuccess());
            accountModel.setAuthToken(response.getAuthToken());
            accountModel.setNickName(response.getNickName());
        } else view.showError(response.getErrMsg());
    }

    private class LoginListener extends Listener {
        @Override
        public void received(Connection connection, Object received) {
            try {
                if (received instanceof LoginResponse) {
                    handleLoginResponse((LoginResponse) received);
                }
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        }
    }
}
