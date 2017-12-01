package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

public interface LoginController extends Controller {

    void sendLogin(String email, String hashedPassword);
}
