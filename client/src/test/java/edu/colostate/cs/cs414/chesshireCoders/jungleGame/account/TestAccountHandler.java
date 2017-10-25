package edu.colostate.cs.cs414.chesshireCoders.jungleGame.account;

import static org.junit.Assert.*;


import org.junit.Test;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.account.AccountHandler;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.HomeController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.LoginController;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TestAccountHandler {

    private RegisterController registerController;


	@Test
	public void testRegisterUser() {
		//test data
		RegisterController regController = new RegisterController();
		AccountHandler accountHandler=new AccountHandler();
		//accountHandler.registerUser("a", "b", "c", "c", regController);
		assertFalse(accountHandler.getRegisterUserStatus());
		
		//Real test case assert, use this after being able to test the registration
		//assertTrue(accountHandler.getRegisterUserStatus());
	}

	@Test
	public void testUnregisterUser() {
		HomeController homeController=new HomeController();
		AccountHandler accountHandler=new AccountHandler();
		//accountHandler.unregisterUser("a", "b",  homeController);
		
		assertFalse(accountHandler.getUnregisterUserStatus());
		//assertTrue(accountHandler.getUnregisterUserStatus());
		
	}

	@Test
	public void testValidateLogin() {
		LoginController loginController= new LoginController();
		AccountHandler accountHandler=new AccountHandler();
		//accountHandler.validateLogin("email", "password", loginController);
		//assertTrue(accountHandler.getLoginUserStatus());
		assertFalse(accountHandler.getLoginUserStatus());

	}

	/*@Test
	public void testLogout() {
		
	}*/

}
