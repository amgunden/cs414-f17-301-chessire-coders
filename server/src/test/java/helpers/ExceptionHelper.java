package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExceptionHelper {

    private boolean expectsAnyException = false;
    private Set<Class<? extends  Exception>> expectedTypes = new HashSet<>();
    private List<Exception> unexpectedExceptions = new ArrayList<>();

    public void expectsAnyException() {
        expectsAnyException = true;
    }

    public void expectsException(Class<? extends Exception> type) {
        expectedTypes.add(type);
    }

    public void handle(Exception e) {
        if (expectsAnyException || expectedTypes.contains(e.getClass())) {
            unexpectedExceptions.add(e);
        } else throw new RuntimeException(e);
    }

    public List<Exception> getUnexpectedExceptions() {
        return unexpectedExceptions;
    }

    public boolean failed() {
        return !unexpectedExceptions.isEmpty();
    }
}
