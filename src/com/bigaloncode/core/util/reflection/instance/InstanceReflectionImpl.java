package com.bigaloncode.core.util.reflection.instance;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.bigaloncode.core.util.reflection.Reflect;
import com.bigaloncode.core.util.reflection.ReflectException;
import com.bigaloncode.core.util.validation.Validate;

public class InstanceReflectionImpl implements InstanceReflection, Get, Set, Call
{
    private Object instance;

    public InstanceReflectionImpl(final Object instance) {
        Validate.argNotNull(instance, "instance");
        this.instance = instance;
    }

    @Override
    public <T> T method(final String name, final Object... args)
    {
        final Method m =
            Reflect.on(instance.getClass()).get().method(name, args);

        return invoke(m, args);
    }

    @SuppressWarnings("unchecked")
    private <T> T invoke(final Method method, final Object... args) {

        Validate.notNull(method, "no such method found");

        if (!method.isAccessible()) {
            method.setAccessible(true);
        }

        try {
            return (T) method.invoke(instance, args);
        }
        catch (final Exception e) {
            throw ReflectException.newMethodInvocationFailure(e,
                                                              instance,
                                                              method.getName(),
                                                              args);
        }
    }

    @Override
    public <T> T method(final Class<? extends Annotation> annotationType,
                        final Object... args) {

        final Method m =
            Reflect.on(instance.getClass()).get().method(annotationType, args);

        return invoke(m, args);
    }

    @Override
    public void field(final String name, final Object value) {

        final Field field = getAccessibleField(name);

        try {
            field.set(instance, value);
        }
        catch (final Exception e) {
            throw new ReflectException("set field failed: " + e, e);
        }
    }

    private Field getAccessibleField(final String name) {
        final Field field =
            Reflect.on(instance.getClass()).get().field(name);

        Validate.notNull(field, "No such field: " + name);

        if (field.isAccessible()) {
            field.setAccessible(true);
        }
        return field;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T field(final String name) {
        final Field field = getAccessibleField(name);

        try {
            return (T) field.get(instance);
        }
        catch (final Exception e) {
            throw new ReflectException("get field failed: " + e, e);
        }

    }

    @Override
    public Get get() {
        return this;
    }

    @Override
    public Set set() {
        return this;
    }

    @Override
    public Call call() {
        return this;
    }

}
