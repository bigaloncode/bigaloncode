package com.bigaloncode.core.type;

import com.bigaloncode.core.util.validation.Validate;

/**
 * A TypeWrapper is simply a type that wraps another type. The purpose of this class is
 * just to make it easier to wrap other classes.
 */
public class TypeWrapper<T> {

    private final T value;

    public TypeWrapper(final T value) {
        Validate.argNotNull(value, "value");
        this.value = value;
    }

    protected T valueImpl() {
        return value;
    }
}
