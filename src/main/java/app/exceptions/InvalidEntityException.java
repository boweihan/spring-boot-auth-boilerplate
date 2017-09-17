package app.exceptions;

import java.util.ArrayList;
import java.util.List;

public class InvalidEntityException extends RuntimeException {
    private static final long serialVersionUID = -8762073181655035705L;

    private final List<String> errors;

    public InvalidEntityException(String message, Iterable<String> errors) {
        super(message);
        List<String> finalErrors = new ArrayList<>();
        errors.forEach(finalErrors::add);
        this.errors = finalErrors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
