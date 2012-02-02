package com.bigaloncode.core.observer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ObserverRegistryTest {

    public static final class ObTypeOne implements ObservationType {
    }

    public static final class ObTypeTwo implements ObservationType {
    }

    // how do the filters get access to the board?
    public static final class MyFilter implements Filter {
        @Override
        public boolean matches(final Object observed) {
            return false;
        }
    }

    public static final class ObserverOne {

        @Observer(value = ObTypeOne.class, filters = { MyFilter.class })
        public void observe(final String value) {
            System.out.println("Observed String: " + value);
        }

        @Observer(ObTypeOne.class)
        public void observe(final Long value) {
            System.out.println("Observed Long: " + value);
        }

        @Observer(ObTypeOne.class)
        public void observe(final List<Integer> values) {
            System.out.println("ObservedList: " + values);
        }

        @Observer(ObTypeOne.class)
        public void observe(final Object value) {
            System.out.println("Observed Object: " + value);
        }
    }

    public static final class ObserverTwo {

        @Observer(ObTypeOne.class)
        public void observe(final Object obj) {
            System.out.println("ObserverTwo: Observed Object: " + obj);
        }

        @Observer(ObTypeTwo.class)
        public void observe(final Boolean value) {
            System.out.println("ObserverTwo: Observed Boolean: " + value);
        }
    }

    @Test
    public void notifySimple() {
        final ObserverRegistry registry = new ObserverRegistry();
        registry.register(new ObserverOne());
        registry.register(new ObserverTwo());

        registry.notify(ObTypeOne.class, "Hi!");
        registry.notify(ObTypeOne.class, 3344L);
        registry.notify(ObTypeTwo.class, 3344L);
        registry.notify(ObTypeOne.class, true);
        registry.notify(ObTypeTwo.class, true);
        registry.notify(ObTypeOne.class, new ArrayList<Integer>());
    }
}
