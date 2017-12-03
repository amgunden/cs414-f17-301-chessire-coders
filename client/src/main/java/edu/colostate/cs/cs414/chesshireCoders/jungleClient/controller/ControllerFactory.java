package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl.GameBoardControllerImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl.HomeControllerImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl.LoginControllerImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller.impl.RegisterControllerImpl;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;

/**
 * This class hides the implementation details of the Controller classes.
 * <p>
 * This could be used to load different controllers if needed.
 */
public class ControllerFactory {

    public static GameBoardController getGameBoardController(View view) {
        return new GameBoardControllerImpl(view);
    }

    public static HomeController getHomeController(View view) {
        return new HomeControllerImpl(view);
    }

    public static LoginController getLoginController(View view) {
        return new LoginControllerImpl(view);
    }

    public static RegisterController getRegisterController(View view) {
        return new RegisterControllerImpl(view);
    }
}
