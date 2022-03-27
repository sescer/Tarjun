package ru.nsu.fit.g19213.tarjun.matchers;

import ru.nsu.fit.g19213.tarjun.utils.TarjunInfo;

import static ru.nsu.fit.g19213.tarjun.utils.TarjunUtil.defaultValueOf;

public class ArgumentMatchers {
    /**
     * Matches any argument.
     */
    public static <T> T any() {
        TarjunInfo.addArgumentMatcher((var o) -> true);
        return null;
    }

    /**
     * Matches any integer,
     */
    public static int anyInt() {
        TarjunInfo.addArgumentMatcher(Integer.class::isInstance);
        return 0;
    }


    /**
     * Matches the exact value passed.
     * @param t the argument to match
     */
    public static <T> T eq(T t) {
        TarjunInfo.addArgumentMatcher(t::equals);
        return t;
    }


    /**
     * Matches any instance of a given class.
     * @param klass class instances of which to match
     */
    public static <T> T anyOfType(Class<T> klass) {
        TarjunInfo.addArgumentMatcher(klass::isInstance);
        return (T) defaultValueOf(klass);
    }

    /**
     * Matches any instance of a given exact class. <br/>
     * This means any subclassed instances will not be matched.
     * @param klass class instances of which to match
     */
    public static <T> T anyOfTypeExact(Class<T> klass) {
        TarjunInfo.addArgumentMatcher((Object o) -> o.getClass().getCanonicalName().equals(klass.getCanonicalName()));
        return (T) defaultValueOf(klass);
    }
}
