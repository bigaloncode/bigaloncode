package com.bigaloncode.core.observer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bigaloncode.core.util.reflection.ClassHierarchyIterator;
import com.bigaloncode.core.util.reflection.Reflect;
import com.bigaloncode.core.util.validation.Validate;

public class ObserverRegistry {

    //
    // Todo: this is shit!
    //
    private static final class ObserverMethod {

        private final Object target;
        private final Method method;
        private final Class<? extends ObservationType> observationType;
        private final Class<?> observedType;

        public ObserverMethod(final Object target, final Method method) {
            this.target = target;
            this.method = method;

            this.observationType = method.getAnnotation(Observer.class).value();
            this.observedType = method.getParameterTypes()[0];

        }

        public Class<? extends ObservationType> observationType() {
            return observationType;
        }

        public boolean matches(final Object observed) {
            return observedType.isAssignableFrom(observed.getClass());
        }

        public void invoke(final Object observed) {
            try {
                method.invoke(target, observed);
            }
            catch (final Exception e) {
                // Assert that this shit should never happen
            }
        }

    }

    private final Map<Class<? extends ObservationType>, List<ObserverMethod>> observers =
        new HashMap<Class<? extends ObservationType>, List<ObserverMethod>>();

    public void register(final Object observer) {

        Validate.argNotNull(observer, "observer");

        final List<Observer> observers =
            Reflect.on(observer.getClass()).get().annotations(Observer.class);

        for (final Class<?> current : new ClassHierarchyIterator(observer)) {
            for (final Method method : current.getMethods()) {
                if (method.isAnnotationPresent(Observer.class)) {
                    register(new ObserverMethod(observer, method));
                }
            }
        }
    }

    private void register(final ObserverMethod method) {

        final Class<? extends ObservationType> observationType =
            method.observationType();

        List<ObserverMethod> registered = observers.get(observationType);

        if (registered == null) {
            registered = new ArrayList<ObserverMethod>();
            observers.put(method.observationType(), registered);
        }

        registered.add(method);
    }

    private void deregister(final Object object) {
        // shit, this is expensive, how do we handle this?
    }

    public void notify(final Class<? extends ObservationType> observationType,
                       final Object observed) {

        final List<ObserverMethod> methods = observers.get(observationType);

        if (methods == null) {
            System.out.println("No observers for type: " + observed);
        }

        for (final ObserverMethod method : methods) {
            if (method.matches(observed)) {
                method.invoke(observed);
            }
        }
    }
}
