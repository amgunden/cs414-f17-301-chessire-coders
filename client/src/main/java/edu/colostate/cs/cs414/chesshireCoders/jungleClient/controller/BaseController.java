package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;

public abstract class BaseController<T extends View> implements Controller {

    protected final T view;

    public BaseController(T view) {
        this.view = view;
    }

    public void dispose() {
    }
}
