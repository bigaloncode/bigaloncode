package com.bigaloncode.core.util.reflection.instance;

import java.lang.annotation.Annotation;

public interface Call {

    public <T> T method(final String name,
                        final Object... args);

    public <T> T method(final Class<? extends Annotation> annotationType,
                        final Object... args);
}
