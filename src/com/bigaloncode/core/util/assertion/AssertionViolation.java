package com.bigaloncode.core.util.assertion;

/**
 * Error that indicates an assertion was violated. Thrown by {@link Assert}
 */
public class AssertionViolation extends Error {

    private static final long serialVersionUID = 1L;

    public AssertionViolation(final String message) {
        super(message);
    }

    public AssertionViolation(final String message,
                              final Throwable cause) {
        super(message, cause);
    }

}