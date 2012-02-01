package com.bigaloncode.core.util.reflection.type;

public interface Call {

    public <T> T constructor(final Object... args);

    public <T> T staticMethod(final String name, final Object... args);
}
