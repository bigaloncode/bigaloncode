package com.bigaloncode.core.util.validation;

import java.util.Collection;

public final class Validate {

    private static final boolean ON = true;

    /**
     * @throws ValidationException
     *             thrown if the given value is null
     */
    public static void argNotNull(final Object value,
                                  final String name)
    {
        if (ON) {
            notNull(value, toMessage(value, name, "not null"));
        }
    }

    /**
     * @return Converts the given value, name, and assertion text into a standard error
     *         message
     */
    private static String toMessage(final Object value,
                                    final String name,
                                    final String assertion) {

        return "Argument: " + name + " failed assertion: " + assertion +
            ". value: '" + value + "'";
    }

    /**
     * @throws ValidationException
     *             thrown if the given value is null
     */
    public static void notNull(final Object value,
                               final String message) {
        if (ON) {
            isTrue(value != null, message);
        }
    }

    /**
     * @throws ValidationException
     *             thrown if the given value is <= 0
     */
    public static void argIsPositive(final int value,
                                     final String name) {
        if (ON) {
            isPositive(value, toMessage(value, name, "is positive"));
        }
    }

    /**
     * @throws ValidationException
     *             thrown if the given value is <= 0
     */
    public static void isPositive(final int value,
                                  final String message) {
        if (ON) {
            isTrue(value > 0, message);
        }
    }

    /**
     * @throws ValidationException
     *             thrown when the given value is empty or null
     */
    public static void argNotEmpty(final String value,
                                   final String name) {
        if (ON) {
            notEmpty(value, toMessage(value, name, "not empty"));
        }
    }

    /**
     * @throws ValidationException
     *             thrown when the given value is empty or null
     */
    public static void notEmpty(final String value,
                                final String message) {
        if (ON) {
            isTrue(value != null && value.length() > 0, message);
        }
    }

    /**
     * @throws ValidationException
     *             thrown if the given values array is null or empty
     */
    public static void argNotEmpty(final Object[] values,
                                   final String name) {
        if (ON) {
            notEmpty(values, toMessage(values, name, "not empty"));
        }
    }

    /**
     * @throws ValidationException
     *             thrown if the given value array is empty or null
     */
    public static void notEmpty(final Object[] values,
                                final String message)
    {
        if (ON) {
            isTrue(values != null && values.length != 0, message);
        }
    }

    /**
     * @throws ValidationException
     *             thrown if the given collection is null or empty
     */
    public static void argNotEmpty(final Collection<?> collection,
                                   final String name) {
        if (ON) {
            notEmpty(collection, toMessage(collection, name, "not empty"));
        }
    }

    /**
     * @throws ValidationException
     *             thrown if the given collection is empty
     */
    public static void notEmpty(final Collection<?> collection,
                                final String message)
    {
        isTrue(collection != null && collection.size() != 0,
               message);
    }

    /**
     * @throws ValidationException
     *             if the given value is false
     */
    public static void isTrue(final boolean value,
                              final String message)
    {
        if (ON) {
            if (!value) {
                throw new ValidationException(message);
            }
        }
    }

    /**
     * @throws ValidationException
     *             thrown with the given message
     */
    public static void fail(final String message) {
        throw new ValidationException(message);
    }

    /**
     * @throws ValidationException
     *             thrown with the given message and cause
     */
    public static void fail(final String message, final Throwable cause) {
        throw new ValidationException(message, cause);
    }

    private Validate() {
    }
}
