package com.bigaloncode.core.util.reflection.type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bigaloncode.core.util.reflection.ClassHierarchyIterator;
import com.bigaloncode.core.util.validation.Validate;

/**
 * 
 * 
 */
public class TypeReflectionImpl
    implements TypeReflection, Get, Call
{
    /**
     * Maps primitive types to their associated wrapper class
     */
    @SuppressWarnings("serial")
    private static Map<Class<?>, Class<?>> primitiveToWrapperMappings =
        new HashMap<Class<?>, Class<?>>() {
            {
                put(Boolean.TYPE, Boolean.class);
                put(Byte.TYPE, Byte.class);
                put(Character.TYPE, Character.class);
                put(Short.TYPE, Short.class);
                put(Integer.TYPE, Integer.class);
                put(Long.TYPE, Long.class);
                put(Double.TYPE, Double.class);
                put(Float.TYPE, Float.class);
            }
        };

    /**
     * The type being inspected
     */
    private final Class<?> inspectionType;

    public TypeReflectionImpl(final Class<?> type) {
        Validate.argNotNull(type, "type");
        this.inspectionType = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Get get() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call call() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field field(final String name) {
        for (final Class<?> current : new ClassHierarchyIterator(inspectionType)) {
            final Field field = findField(current, name);
            if (field != null) {
                return field;
            }
        }
        return null;
    }

    private Field findField(final Class<?> current,
                            final String name) {
        for (final Field field : current.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Method method(final String name, final Class<?>... argTypes) {
        for (final Class<?> current : new ClassHierarchyIterator(inspectionType)) {
            for (final Method m : current.getDeclaredMethods()) {
                if (m.getName().equals(name) &&
                    matches(m.getParameterTypes(), argTypes)) {
                    return m;
                }
            }
        }
        return null;
    }

    private boolean matches(final Class<?>[] declaredTypes,
                            final Class<?>[] actualTypes) {

        //
        // If the arrays are the same size then we cannot have
        // a match
        //
        if (declaredTypes.length != actualTypes.length) {
            return false;
        }

        //
        // Make sure each actual type can be assigned to each declared type
        //
        for (int i = 0; i < actualTypes.length; i++) {
            if (!matches(declaredTypes[i], actualTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indicates if an instance of the given declaredType is assignable from the given
     * actual type.
     */
    private boolean matches(final Class<?> declaredType,
                            final Class<?> actualType) {

        //
        // If the actual type is null then it is assignable to any
        // declared type provided it is not a primitive
        //
        if (actualType == null) {
            return !declaredType.isPrimitive();
        }

        return wrapper(declaredType).isAssignableFrom(wrapper(actualType));
    }

    /**
     * Returns the wrapper associated with the given type if it is a primitive. If it is
     * not the given type is simply echoed.
     */
    private Class<?> wrapper(final Class<?> t) {
        final Class<?> wrapped =
            primitiveToWrapperMappings.get(t);

        return wrapped != null ? wrapped : t;
    }

    @Override
    public Method method(final String name, final Object... args) {
        return method(name, types(args));
    }

    /**
     * Utility methods that creates an array containing the associated class of each given
     * object.
     */
    private Class<?>[] types(final Object[] objs) {
        final Class<?>[] result = new Class<?>[objs.length];
        for (int i = 0; i < objs.length; ++i) {
            final Object obj = objs[i];
            result[i] = obj != null ? obj.getClass() : null;
        }
        return result;
    }

    @Override
    public Method method(final Class<? extends Annotation> annotationType,
                         final Class<?>... args)
    {
        for (final Class<?> current : new ClassHierarchyIterator(inspectionType)) {
            for (final Method m : current.getDeclaredMethods()) {
                if (m.getAnnotation(annotationType) != null &&
                    matches(m.getParameterTypes(), args)) {
                    return m;
                }
            }
        }
        return null;
    }

    @Override
    public Method method(final Class<? extends Annotation> annotationType,
                         final Object... args) {
        return method(annotationType, types(args));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> Constructor<T> constructor(final Class<?>... argTypes) {

        for (final Constructor<?> constructor : inspectionType.getDeclaredConstructors()) {
            if (matches(constructor.getParameterTypes(),
                        argTypes)) {
                return (Constructor<T>) constructor;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T constructor(final Object... args) {

        final Class<?>[] argTypes = types(args);

        final Constructor<T> constructor =
            constructor(argTypes);

        if (constructor == null) {
            Validate.fail("No constructor found for type: " + inspectionType +
                " that matches arguments: " + Arrays.toString(args));
        }

        try {
            return constructor.newInstance(args);
        }
        catch (final Exception e) {
            Validate.fail("Failed to invoke constructor of type: " + inspectionType +
                " with arguments: " + args, e);
        }

        return null; // dead code for the compiler
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Annotation> T annotation(final Class<T> type) {

        final List<T> annotations = annotations(type);
        if (!annotations.isEmpty()) {
            return annotations.get(0);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Annotation> List<T> annotations(final Class<T> annotationType) {
        final List<T> result = new ArrayList<T>();

        for (final Class<?> current : new ClassHierarchyIterator(inspectionType)) {
            for (final Annotation annotation : current.getAnnotations()) {
                addMatches(annotation, annotationType, result);
            }
        }
        return result;
    }

    /**
     * Inspect the given annotation and if it or any annotation it has equal the
     * targetType adds them to the result list.
     */
    @SuppressWarnings("unchecked")
    public <T extends Annotation> void addMatches(final Annotation toInspect,
                                                  final Class<T> targetType,
                                                  final List<T> result) {

        //
        // If the given annotation equals the given type then we have found a match
        //
        if (toInspect.annotationType().equals(targetType)) {
            result.add((T) toInspect);
        }

        //
        // Now we want to recurse and check all the annotations on the
        // annotation to see if any of them are the targetType. We can't do this
        // with the standard java annotations though because it would cause us to
        // recurse forever. So if we are inspecting one of those annotations we are
        // done.
        //
        if (isJavaLangAnnotation(toInspect)) {
            return;
        }

        //
        // Recurse and inspect all of this annotation's annotations.
        //
        for (final Annotation annotation : toInspect.annotationType().getAnnotations()) {
            addMatches(annotation, targetType, result);
        }
    }

    /**
     * Indicates if the given annotation is in the java.lang.annotation package.
     */
    private boolean isJavaLangAnnotation(final Annotation annotation) {
        return annotation.annotationType().getPackage().
            getName().equals("java.lang.annotation");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T staticMethod(final String name, final Object... args) {
        final Method method = method(name, types(args));
        if (method == null) {
            Validate.fail("Type: " + inspectionType + ", contains no method: " + name +
                ", that can accept arguments: " + Arrays.toString(args));
        }

        try {
            return (T) method.invoke(null, args);
        }
        catch (final Exception e) {
            Validate
                .fail("Failed to invoke method: " + name + " of type: " + inspectionType +
                    " with arguments: " + Arrays.toString(args));
        }

        return null; // dead code for compiler
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Class<U> typeArgument(final int index) {

        for (Class<?> current = inspectionType; current != Object.class; current = current
            .getSuperclass()) {

            final Type type = current.getGenericSuperclass();

            if (type instanceof ParameterizedType) {
                final ParameterizedType pType = (ParameterizedType) type;

                final Type[] types = pType.getActualTypeArguments();

                if (types.length > index) {
                    final Type argType = types[index];

                    if (argType instanceof Class) {
                        return (Class<U>) argType;
                    }
                }
            }
        }

        return null;
    }
}
