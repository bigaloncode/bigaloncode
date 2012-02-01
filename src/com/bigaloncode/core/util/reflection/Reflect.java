package com.bigaloncode.core.util.reflection;

import com.bigaloncode.core.util.reflection.instance.InstanceReflection;
import com.bigaloncode.core.util.reflection.instance.InstanceReflectionImpl;
import com.bigaloncode.core.util.reflection.type.TypeReflection;
import com.bigaloncode.core.util.reflection.type.TypeReflectionImpl;

public class Reflect {

    public static <T> TypeReflection on(final Class<T> type) {
        return new TypeReflectionImpl(type);
    }

    public static InstanceReflection on(final Object instance) {
        return new InstanceReflectionImpl(instance);
    }
}
