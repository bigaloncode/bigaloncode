package com.bigaloncode.core.util.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassHierarchyIteratorTest {

    public static class A {
    }

    public static class B extends A {
    }

    public static class C extends B {
    }

    @Test
    public void iterate()
    {
        final ClassHierarchyIterator iterator = new ClassHierarchyIterator(C.class);

        assertTrue(iterator.hasNext());
        assertEquals(C.class, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(B.class, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(A.class, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(Object.class, iterator.next());

        assertFalse(iterator.hasNext());
    }

}