package edu.colostate.cs.cs414.chesshireCoders.jungleClient.view;

public interface RegisterView extends View {
    void registrationSuccess();

    void registrationFailure(String errorMessage);
}
