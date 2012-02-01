package com.bigaloncode.core.util.validation;

/**
 * Exception thrown when there is a validation failure.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final String message,
                               final Throwable cause) {
        super(message, cause);
    }

}