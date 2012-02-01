package com.bigaloncode.core.util.reflection;

import java.util.Iterator;

import com.bigaloncode.core.util.validation.Validate;

/**
 * An Iterator that walks a class hierarchy.
 */
public class ClassHierarchyIterator implements Iterator<Class<?>>, Iterable<Class<?>> {

    private Class<?> current;

    public ClassHierarchyIterator(final Object source)
    {
        Validate.argNotNull(source, "source");
        this.current = source.getClass();
    }

    public ClassHierarchyIterator(final Class<?> source)
    {
        Validate.argNotNull(source, "source");
        this.current = source;
    }

    /**
     * Indicates if there is another super class in the hierarchy
     */
    @Override
    public boolean hasNext() {
        return current != null;
    }

    /**
     * Returns the next class in the hierarchy
     */
    @Override
    public Class<?> next() {
        final Class<?> result = current;
        current = current.getSuperclass();
        return result;
    }

    /**
     * @throws UnsupportedOperationException
     *             don't call this.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("ClassHierarchyIterator:remove");
    }

    @Override
    public Iterator<Class<?>> iterator() {
        return this;
    }

}
