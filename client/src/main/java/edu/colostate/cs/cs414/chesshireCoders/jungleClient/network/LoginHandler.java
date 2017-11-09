package edu.colostate.cs.cs414.chesshireCoders.jungleClient.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import javafx.application.Platform;

public class LoginHandler extends Listener {

    private LoginController loginController;

    public LoginHandler(LoginController controller) {
        this.loginController = controller;
    }

    public void sendLogin(String email, String hashedPassword) {
        LoginRequest request = new LoginRequest(email, hashedPassword);
        App.getJungleClient().sendMessage(request);
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof LoginResponse) {
            handleLoginResponse((LoginResponse) received);
        }
    }

    private void handleLoginResponse(LoginResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
                loginController.loginSuccess();
                AuthTokenManager.getInstance().setAuthToken(response.getAuthToken());
            } else {
                loginController.loginFailure(response.getErrMsg());
            }
        });
    }


	public void sendLogout(String email) {
		// TODO Auto-generated method stub
		
	}
   
}
