package com.bigaloncode.core.observer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Observer {

    public Class<? extends ObservationType> value();

    public Class<? extends Filter>[] filters() default {};
}
