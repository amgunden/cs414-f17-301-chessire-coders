package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view;

public interface LoginView extends View {

    void loginFailure(String errorMessage);

    void loginSuccess();
}
