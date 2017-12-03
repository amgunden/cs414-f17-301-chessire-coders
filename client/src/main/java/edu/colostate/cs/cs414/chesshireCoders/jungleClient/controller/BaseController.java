package edu.colostate.cs.cs414.chesshireCoders.jungleClient.controller;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.view.View;

public abstract class BaseController {

    protected View view;

    public BaseController(View view) {
        this.view = view;
    }

    public void dispose() {
    }
}
