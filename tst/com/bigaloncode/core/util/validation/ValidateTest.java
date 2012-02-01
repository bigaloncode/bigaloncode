package com.bigaloncode.core.util.validation;

import java.util.ArrayList;

import org.junit.Test;

public class ValidateTest {

    private static final String DEFAULT_ARG_NAME = "argument";
    private static final String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";

    @Test(expected = ValidationException.class)
    public void argNotNullThrowsWhenGivenNull() {
        Validate.argNotNull(null, DEFAULT_ARG_NAME);
    }

    @Test
    public void argNotNullDoesNotThrowWhenGivenNonNull() {
        Validate.argNotNull("Yay", DEFAULT_ARG_NAME);
    }

    @Test(expected = ValidationException.class)
    public void notNullThrowsWhenGivenNull() {
        Validate.notNull(null, DEFAULT_MESSAGE);
    }

    @Test
    public void notNullDoesNotThrowWhenGivenNonNull() {
        Validate.notNull("Yay", DEFAULT_MESSAGE);
    }

    @Test(expected = ValidationException.class)
    public void isPositiveThrowsWhenGivenZero() {
        Validate.argIsPositive(0, DEFAULT_ARG_NAME);
    }

    @Test(expected = ValidationException.class)
    public void argIsPositiveThrowsWhenGivenNegativeNumber() {
        Validate.argIsPositive(-1, DEFAULT_ARG_NAME);
    }

    @Test
    public void argIsPositiveDoesNotThrowWhenGivenPositiveNumber() {
        Validate.argIsPositive(1, DEFAULT_ARG_NAME);
    }

    @Test(expected = ValidationException.class)
    public void argNotEmptyThrowsWhenGivenEmptyString() {
        Validate.argNotEmpty("", DEFAULT_ARG_NAME);
    }

    @Test(expected = ValidationException.class)
    public void argNotEmptyThrowsWhenGivenEmptyArray() {
        Validate.argNotEmpty(new Object[] {}, DEFAULT_ARG_NAME);
    }

    @Test(expected = ValidationException.class)
    public void argNotEmptyThrowsWhenGivenEmptyCollection() {
        Validate.argNotEmpty(new ArrayList<Object>(), DEFAULT_ARG_NAME);
    }

    @Test(expected = ValidationException.class)
    public void isTrueThrowsWhenGivenFalse() {
        Validate.isTrue(false, DEFAULT_MESSAGE);
    }

    @Test
    public void isTrueDoesNotThrowWhenGivenTrue() {
        Validate.isTrue(true, DEFAULT_MESSAGE);
    }

    @Test(expected = ValidationException.class)
    public void failThrows() {
        Validate.fail(DEFAULT_MESSAGE);
    }

    @Test(expected = ValidationException.class)
    public void failWithCauseThrows() {
        Validate.fail(DEFAULT_MESSAGE, new RuntimeException("Yay!"));
    }
}
