package com.bigaloncode.core.util.assertion;

import java.util.Collection;

/**
 * Assertion Utility class used to prevent/catch programmer error. Why roll your own class
 * and not use java's built in assertions? Well, One, I like to have my own class because
 * I like to build higher level methods like notEmpty on strings and isPositive. And two,
 * because I like have standardized error messages.
 * 
 * Why not use commons lang Validate or some other common library? Mainly because I like
 * to be in control of my foundation classes. I like to be able to add methods that make
 * sense to my application.
 */
public final class Assert {

    private static final boolean ON = true;

    /**
     * @throws AssertionViolation
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
     * @throws AssertionViolation
     *             thrown if the given value is null
     */
    public static void notNull(final Object value,
                               final String message) {
        if (ON) {
            isTrue(value != null, message);
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown if the given value is <= 0
     */
    public static void argIsPositive(final int value,
                                     final String name) {
        if (ON) {
            isPositive(value, toMessage(value, name, "is positive"));
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown if the given value is <= 0
     */
    public static void isPositive(final int value,
                                  final String message) {
        if (ON) {
            isTrue(value > 0, message);
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown when the given value is empty or null
     */
    public static void argNotEmpty(final String value,
                                   final String name) {
        if (ON) {
            notEmpty(value, toMessage(value, name, "not empty"));
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown when the given value is empty or null
     */
    public static void notEmpty(final String value,
                                final String message) {
        if (ON) {
            isTrue(value != null && value.length() > 0, message);
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown if the given values array is null or empty
     */
    public static void argNotEmpty(final Object[] values,
                                   final String name) {
        if (ON) {
            notEmpty(values, toMessage(values, name, "not empty"));
        }
    }

    /**
     * @throws AssertionViolation
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
     * @throws AssertionViolation
     *             thrown if the given collection is null or empty
     */
    public static void argNotEmpty(final Collection<?> collection,
                                   final String name) {
        if (ON) {
            notEmpty(collection, toMessage(collection, name, "not empty"));
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown if the given collection is empty
     */
    public static void notEmpty(final Collection<?> collection,
                                final String message)
    {
        isTrue(collection != null && collection.size() != 0,
               message);
    }

    /**
     * @throws AssertionViolation
     *             if the given value is false
     */
    public static void isTrue(final boolean value,
                              final String message)
    {
        if (ON) {
            if (!value) {
                throw new AssertionViolation(message);
            }
        }
    }

    /**
     * @throws AssertionViolation
     *             thrown with the given message
     */
    public static void fail(final String message) {
        throw new AssertionViolation(message);
    }

    /**
     * @throws AssertionViolation
     *             thrown with the given message and cause
     */
    public static void fail(final String message, final Throwable cause) {
        throw new AssertionViolation(message, cause);
    }

    private Assert() {
    }
}
