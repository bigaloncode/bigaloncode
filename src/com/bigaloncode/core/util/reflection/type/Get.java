package com.bigaloncode.core.util.reflection.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public interface Get {

    public Field field(final String name);

    public Method method(final String name, final Class<?>... args);

    public Method method(final String name, final Object... args);

    public Method method(final Class<? extends Annotation> annotationType,
                         final Class<?>... args);

    public Method method(final Class<? extends Annotation> annotationType,
                         final Object... args);

    public <T> Constructor<T> constructor(final Class<?>... args);

    public <T extends Annotation> T annotation(final Class<T> type);

    public <T extends Annotation> List<T> annotations(final Class<T> type);

    public <U> Class<U> typeArgument(final int index);
}
