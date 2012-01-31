package com.bigaloncode.core.util.assertion;

import java.util.ArrayList;

import org.junit.Test;

public class AssertTest {

    private static final String DEFAULT_ARG_NAME = "argument";
    private static final String DEFAULT_MESSAGE = "DEFAULT_MESSAGE";

    @Test(expected = AssertionViolation.class)
    public void argNotNullThrowsWhenGivenNull() {
        Assert.argNotNull(null, DEFAULT_ARG_NAME);
    }

    @Test
    public void argNotNullDoesNotThrowWhenGivenNonNull() {
        Assert.argNotNull("Yay", DEFAULT_ARG_NAME);
    }

    @Test(expected = AssertionViolation.class)
    public void notNullThrowsWhenGivenNull() {
        Assert.notNull(null, DEFAULT_MESSAGE);
    }

    @Test
    public void notNullDoesNotThrowWhenGivenNonNull() {
        Assert.notNull("Yay", DEFAULT_MESSAGE);
    }

    @Test(expected = AssertionViolation.class)
    public void isPositiveThrowsWhenGivenZero() {
        Assert.argIsPositive(0, DEFAULT_ARG_NAME);
    }

    @Test(expected = AssertionViolation.class)
    public void argIsPositiveThrowsWhenGivenNegativeNumber() {
        Assert.argIsPositive(-1, DEFAULT_ARG_NAME);
    }

    @Test
    public void argIsPositiveDoesNotThrowWhenGivenPositiveNumber() {
        Assert.argIsPositive(1, DEFAULT_ARG_NAME);
    }

    @Test(expected = AssertionViolation.class)
    public void argNotEmptyThrowsWhenGivenEmptyString() {
        Assert.argNotEmpty("", DEFAULT_ARG_NAME);
    }

    @Test(expected = AssertionViolation.class)
    public void argNotEmptyThrowsWhenGivenEmptyArray() {
        Assert.argNotEmpty(new Object[] {}, DEFAULT_ARG_NAME);
    }

    @Test(expected = AssertionViolation.class)
    public void argNotEmptyThrowsWhenGivenEmptyCollection() {
        Assert.argNotEmpty(new ArrayList<Object>(), DEFAULT_ARG_NAME);
    }

    @Test(expected = AssertionViolation.class)
    public void isTrueThrowsWhenGivenFalse() {
        Assert.isTrue(false, DEFAULT_MESSAGE);
    }

    @Test
    public void isTrueDoesNotThrowWhenGivenTrue() {
        Assert.isTrue(true, DEFAULT_MESSAGE);
    }

    @Test(expected = AssertionViolation.class)
    public void failThrows() {
        Assert.fail(DEFAULT_MESSAGE);
    }

    @Test(expected = AssertionViolation.class)
    public void failWithCauseThrows() {
        Assert.fail(DEFAULT_MESSAGE, new RuntimeException("Yay!"));
    }
}
