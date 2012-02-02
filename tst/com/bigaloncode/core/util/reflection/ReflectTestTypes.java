package com.bigaloncode.core.util.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class ReflectTestTypes {

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Entity {
        public String value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Attribute {
        public Class<?> value();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Attribute(Awesome.class)
    public static @interface Awesome {
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Attribute(Fast.class)
    public static @interface Fast {
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Attribute(Intelligent.class)
    public static @interface Intelligent {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Observer {
    }

    @Entity("Person")
    public static class Person {

        private String name;
        private List<Person> children = new ArrayList<Person>();

        public static String getClassName() {
            return Person.class.getName();
        }

        public Person() {
            this.name = getClass().getSimpleName();
        }

        public Person(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void add(final Person child) {
            children.add(child);
        }

        public void add(final List<Person> children) {
            children.addAll(children);
        }

        @Observer
        public void notify(final String name) {
        }

        @Observer
        public void notify(final Person person) {
        }
    }

    @Awesome
    @Fast
    public static class Al extends Person {

        @Observer
        public void notify(final Al al) {
        }
    }

    public static class Parent<T> {

    }

    public static class Child extends Parent<String> {
    }

}
