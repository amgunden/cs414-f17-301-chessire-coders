package edu.colostate.cs.cs414.chesshireCoders.jungleClient.account;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.AuthTokenManager;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.GetUserRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.GetUserResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;
import javafx.application.Platform;

public class AccountHandler extends Listener {

    private HomeController homeController;

    private boolean registerUserStatus;

    private boolean loginUserStatus;

	private boolean unregisterUserStatus;

    public AccountHandler(HomeController homeController) {
        this.homeController = homeController;
        App.getJungleClient().addListener(this);
    }

    public static void requestUserInfo(String nickName) {
    	AuthToken token = AuthTokenManager.getInstance().getToken();
    	GetUserRequest request = new GetUserRequest(token, nickName);
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
        Platform.runLater(() -> {
            if (response.isSuccess()) {
                //homeController.displayNickName();
            } else {
                homeController.printGetUserError(response.getErrMsg());
            }
        });
    }

    public void setUnregisterUserStatus(boolean unregisterUserStatus) {
        this.unregisterUserStatus = unregisterUserStatus;
    }

    public boolean getUnregisterUserStatus() {
        return this.registerUserStatus;
    }

    public void setRegisterUserStatus(boolean registerUserStatus) {
        this.unregisterUserStatus = registerUserStatus;
    }

    public boolean getRegisterUserStatus() {
        return this.registerUserStatus;
    }

    public void setLoginUserStatus(boolean loginUserStatus) {
        this.loginUserStatus = loginUserStatus;
    }

    public boolean getLoginUserStatus() {
        return this.loginUserStatus;
    }


    public void unregisterUser(HomeController homeController) {
        UnRegisterRequest request = new UnRegisterRequest();
        request.setAuthToken(AuthTokenManager.getInstance().getToken());
        App.getJungleClient().sendMessage(request);
    }
}
