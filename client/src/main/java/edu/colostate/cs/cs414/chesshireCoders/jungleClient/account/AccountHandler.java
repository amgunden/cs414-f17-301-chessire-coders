package edu.colostate.cs.cs414.chesshireCoders.jungleClient.account;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.App;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.RegisterController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.LoginRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests.UnRegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.LoginResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.RegisterResponse;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses.UnRegisterResponse;

public class AccountHandler {

    private JungleClient client;

    private Listener registerResponseListener;
    private Listener loginResponseListener;
    private Listener unregisterResponseListener;
    private Listener logoutResponseListener;

    private RegisterController registerController;
    private LoginController loginController;
    private HomeController homeController;

    private boolean registerUserStatus = false;
    private boolean unregisterUserStatus = false;
    private boolean loginUserStatus = false;

    public AccountHandler() {
        this.client = App.getJungleClient();
    }

    public void registerUser(String email, String nickname, String pw, String pwReentered, RegisterController regController) {
        registerController = regController;

        //String message = "";
        RegisterRequest request = new RegisterRequest(pw, email, nickname, "nameFirst", "nameLast");

        registerResponseListener = new Listener.ThreadedListener(new FilteredListener<RegisterResponse>(RegisterResponse.class) {
            @Override
            public void run(Connection connection, RegisterResponse received) {

                client.removeListener(registerResponseListener);

                if (received.isSuccess()) { // I'll add some getters and setters in my PR, currently everything is package private
                    setRegisterUserStatus(true);
                    registerController.registrationSuccess();
                } else {
                    registerController.registrationFailure();
                }

            }
        });

        client.sendMessageExpectsResponse(request, registerResponseListener);

    }


    public void unregisterUser(String email, String pw, HomeController homeCont) {
        //homeController = homeCont;

        UnRegisterRequest request = new UnRegisterRequest(pw, email);

        unregisterResponseListener = new Listener.ThreadedListener(new FilteredListener<UnRegisterResponse>(UnRegisterResponse.class) {
            @Override
            public void run(Connection connection, UnRegisterResponse received) {

                client.removeListener(registerResponseListener);

                if (received.isSuccess()) { // I'll add some getters and setters in my PR, currently everything is package private
                    registerController.registrationSuccess();
                    setUnregisterUserStatus(true);
                } else {
                    registerController.registrationFailure();
                }

            }
        });

        client.sendMessageExpectsResponse(request, unregisterResponseListener);

    }


    // Thinking this method could return the User's ID if the login is successful
    public void validateLogin(String email, String pw, LoginController loginCont) {
        loginController = loginCont;

        LoginRequest loginRequest = new LoginRequest(email, pw);

        loginResponseListener = new Listener.ThreadedListener(new FilteredListener<LoginResponse>(LoginResponse.class) {
            @Override
            public void run(Connection connection, LoginResponse received) {

                client.removeListener(loginResponseListener);

                if (received.isSuccess()) { // I'll add some getters and setters in my PR, currently everything is package private
                    loginController.loginSuccess();
                    setLoginUserStatus(true);
                } else {
                    loginController.loginFailure();
                }

            }
        });

        client.sendMessageExpectsResponse(loginRequest, loginResponseListener);

    }

    public void logout(String accessToken) {


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


}
