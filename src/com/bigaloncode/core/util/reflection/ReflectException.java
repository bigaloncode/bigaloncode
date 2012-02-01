package com.bigaloncode.core.util.reflection;

import java.util.Arrays;

/**
 * Exception thrown by the {@link Reflect} object when reflection fails.
 */
public class ReflectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReflectException(final String message,
                            final Throwable cause) {
        super(message, cause);
    }

    public ReflectException(final Throwable cause) {
        super(cause);
    }

    public static ReflectException newMethodInvocationFailure(final Throwable cause,
                                                              final Object target,
                                                              final String name,
                                                              final Object... args) {
        return new ReflectException("Failed to invoke method: " + name + " on target: " + target +
                                        ", with args: " + Arrays.toString(args) + ". Cause: " + cause,
                                    cause);
    }

}
