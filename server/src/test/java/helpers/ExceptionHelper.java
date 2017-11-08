package helpers;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHelper {

    private boolean expectsException = false;
    private List<Exception> exceptions = new ArrayList<>();

    public void expectsException() {
        expectsException = true;
    }

    public void add(Exception e) {
        if (!expectsException) {
            throw new RuntimeException(e);
        }
        exceptions.add(e);
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}
