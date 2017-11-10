package edu.colostate.cs.cs414.chesshireCoders.jungleClient.account;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;
import javafx.application.Platform;

public class AccountHandler extends Listener{

    private HomeController homeController;

    private User userInfo;

    public AccountHandler(HomeController homeController) {
        this.homeController = homeController;
    }

    public static void getUserInfo(String email) {
        GetUserRequest request = new GetUserRequest(AuthTokenManager.getInstance().getAuthToken().getToken(), email);
        App.getJungleClient().sendMessage(request);
    }

    @Override
    public void received(Connection connection, Object received) {
        if (received instanceof GetUserResponse) {
            handleGetUserResponse((GetUserResponse) received);
        }
    }

    private void handleGetUserResponse(GetUserResponse response) {
        // JavaFX does not allow UI updates from non-UI threads.
        Platform.runLater(()->{
            if (response.isSuccess()) {
                userInfo = response.getUser();
            } else {
            	homeController.printGetUserError(response.getErrMsg());
            }
        });
    }
   



}
