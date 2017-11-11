package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExceptionHelper {

    private boolean expectsAnyException = false;
    private Set<Class<? extends  Exception>> expectedTypes;
    private List<Exception> unexpectedExceptions = new ArrayList<>();

    public void expectsException() {
        expectsAnyException = true;
    }

    public void expectExceptionType(Class<? extends Exception> type) {
        expectedTypes = new HashSet<>();
    }

    public void handle(Exception e) {
        if (!expectsAnyException) {
            throw new RuntimeException(e);
        }
        unexpectedExceptions.add(e);
    }

    public List<Exception> getUnexpectedExceptions() {
        return unexpectedExceptions;
    }

    public boolean failed() {
        return !unexpectedExceptions.isEmpty();
    }
}
