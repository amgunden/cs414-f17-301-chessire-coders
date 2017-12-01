package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import java.io.IOException;

public interface LoginController extends Controller {

    void sendLogin(String email, String hashedPassword) throws IOException;
}
