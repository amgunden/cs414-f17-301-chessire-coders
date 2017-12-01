package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import java.io.IOException;

public interface RegisterController extends Controller {

    void sendRegistration(String email, String nickname, String hashedPassword) throws IOException;
}
