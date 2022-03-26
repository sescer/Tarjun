package ru.nsu.fit.g19213.tarjun.argumentmatchers;

import ru.nsu.fit.g19213.tarjun.Tarjun;
import ru.nsu.fit.g19213.tarjun.Util;

import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class ArgumentMatchers {
    //TODO implement more matchers and test the ones already made
    private ArgumentMatchers() { }

    public static Object any() {
        Tarjun.currentStubbingMatcherQueue.add((Object o) -> true);
        return null;
    }

    public static <T> T anyIn(Collection<T> coll) {
        return (T) anyIn(coll.toArray());
    }

    @SafeVarargs
    public static <T> T anyIn(T... args) {
        var arr2 = Arrays.copyOf(args, args.length);
        Tarjun.currentStubbingMatcherQueue.add((Object t1) -> Arrays.asList(arr2).contains(t1));

        Class<T> klass = (Class<T>) args[0].getClass();
        return (T) Util.defaultValueOf(klass);
    }

    public static int anyInt() {
        Tarjun.currentStubbingMatcherQueue.add(Integer.class::isInstance);
        return 0;
    }

    public static Object anyOfType(Class<?> klass) {
        Tarjun.currentStubbingMatcherQueue.add(klass::isInstance);
        return null;
    }

    public static Object anyOfExactType(Class<?> klass) {
        Tarjun.currentStubbingMatcherQueue.add(
                (Object o) ->
                        o.getClass().getCanonicalName().equals(klass.getCanonicalName())
        );
        return null;
    }

    public static <T> T argThat(Predicate<T> predicate) {
        Tarjun.currentStubbingMatcherQueue.add((Object o) -> predicate.test((T)o));

        return (T) Util.defaultValueOf((Class<T>) predicate.getClass().getTypeParameters()[0].getBounds()[0]);
    }

}
