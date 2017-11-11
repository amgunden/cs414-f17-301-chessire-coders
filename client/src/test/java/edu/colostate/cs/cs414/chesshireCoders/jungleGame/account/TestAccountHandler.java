package edu.colostate.cs.cs414.chesshireCoders.jungleGame.account;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.app.RegisterController;

public class TestAccountHandler {

    private RegisterController registerController;
    
    /*

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
	public void testRegisterUserAlreadyRegistered() {
		//test data
		RegisterController regController = new RegisterController();
		AccountHandler accountHandler=new AccountHandler();
		//accountHandler.registerUser("a", "b", "c", "c", regController);
		
		RegisterController regController2 = new RegisterController();
		AccountHandler accountHandler2=new AccountHandler();
		//accountHandler2.registerUser("a", "b", "c", "c", regController2);
		
		assertFalse(accountHandler2.getRegisterUserStatus());
		
		
	}
	
	@Test
	public void testRegisterUserFailure() {
		//test data
		RegisterController regController = new RegisterController();
		AccountHandler accountHandler=new AccountHandler();
		//accountHandler.registerUser("a", " ", "c", "c", regController);
		
		
		assertFalse(accountHandler.getRegisterUserStatus());
		
		
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
