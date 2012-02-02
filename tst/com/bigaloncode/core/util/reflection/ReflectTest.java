package com.bigaloncode.core.util.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bigaloncode.core.util.reflection.ReflectTestTypes.Al;
import com.bigaloncode.core.util.reflection.ReflectTestTypes.Attribute;
import com.bigaloncode.core.util.reflection.ReflectTestTypes.Child;
import com.bigaloncode.core.util.reflection.ReflectTestTypes.Entity;
import com.bigaloncode.core.util.reflection.ReflectTestTypes.Observer;
import com.bigaloncode.core.util.reflection.ReflectTestTypes.Person;

public class ReflectTest {

    private Method personAddListMethod;
    private Method personStringObserverMethod;
    private Method personPersonObserverMethod;
    private Method alAlObserverMethod;

    @Before
    public void reflectAndStore() throws Exception {
        personAddListMethod = Person.class.getDeclaredMethod("add", List.class);
        personStringObserverMethod = Person.class.getMethod("notify", String.class);
        personPersonObserverMethod = Person.class.getMethod("notify", Person.class);
        alAlObserverMethod = Al.class.getMethod("notify", Al.class);
    }

    @Test
    public void testSimpleGetField() throws Exception {

        assertEquals(Person.class.getDeclaredField("name"),
                     Reflect.on(Person.class).get().field("name"));

    }

    @Test
    public void testSimpleGetMethod() {
        assertEquals(personAddListMethod,
                     Reflect.on(Person.class).get().
                         method("add", ArrayList.class));

    }

    @Test
    public void testSimpleGetMethodWithObjectArgs() {
        assertEquals(personAddListMethod,
                     Reflect.on(Person.class).get().
                         method("add", new ArrayList<Person>()));
    }

    @Test
    public void testHierarchicalGetMethod() {
        assertEquals(personAddListMethod,
                     Reflect.on(Al.class).get().
                         method("add", new ArrayList<Person>()));
    }

    @Test
    public void testSimpleGetMethodByAnnotation() {
        assertEquals(personStringObserverMethod,
                     Reflect.on(Al.class).get().
                         method(Observer.class, String.class));
        assertEquals(personPersonObserverMethod,
                     Reflect.on(Al.class).get().
                         method(Observer.class, Person.class));
    }

    @Test
    public void testHierarchicalGetMethodByAnnotation() {
        assertEquals(personStringObserverMethod,
                     Reflect.on(Al.class).get().
                         method(Observer.class, String.class));
        assertEquals(personPersonObserverMethod,
                     Reflect.on(Al.class).get().
                         method(Observer.class, Person.class));
        assertEquals(alAlObserverMethod,
                     Reflect.on(Al.class).get().
                         method(Observer.class, Al.class));
    }

    @Test
    public void testSimpleGetConstructor() throws Exception {
        assertEquals(Person.class.getConstructor(String.class),
                     Reflect.on(Person.class).get().constructor(String.class));
    }

    @Test
    public void testSimpleGetAnnotation() {
        assertEquals(Person.class.getAnnotation(Entity.class),
                     Reflect.on(Person.class).get().annotation(Entity.class));
    }

    @Test
    public void testHierarchicalGetAnnotation() {

        final Attribute attribute =
            Reflect.on(Al.class).get().annotation(Attribute.class);

        assertNotNull(attribute);
    }

    @Test
    public void testHiearchicalGetAnnotations() {

        final List<Attribute> attributes =
            Reflect.on(Al.class).get().annotations(Attribute.class);

        assertEquals(2, attributes.size());
    }

    @Test
    public void getTypeArgument() {
        assertEquals(String.class,
                     Reflect.on(Child.class).get().typeArgument(0));
    }

    @Test
    public void callConstructor() {
        final Person p =
            Reflect.on(Person.class).call().constructor("Al");
        assertEquals("Al", p.getName());
    }

    @Test
    public void callStaticMethod() {
        assertEquals(Person.class.getName(),
                     Reflect.on(Person.class).call().
                         staticMethod("getClassName"));
    }

}
