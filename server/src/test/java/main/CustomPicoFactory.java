package main;

import cucumber.runtime.java.picocontainer.PicoFactory;

/**
 * Extension of the standard PicoContainer ObjectFactory
 * which will register the proper AutomatioApi implementation
 * based on a system property.
 */
public class CustomPicoFactory extends PicoFactory {
    public CustomPicoFactory() {
        addClass(World.class);
    }
}