package ru.nsu.fit.g19213.tarjun.matchers;

import ru.nsu.fit.g19213.tarjun.utils.TarjunInfo;

import java.util.Objects;
import java.util.function.Predicate;

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
     * Matches any string,
     */
    public static String anyStr() {
        TarjunInfo.addArgumentMatcher(String.class::isInstance);
        return "";
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
     * Matches any value passed that is not equal to the one passed.
     * @param t the argument to match
     */
    public static <T> T notEq(T t) {
        TarjunInfo.addArgumentMatcher((var o) -> !t.equals(o));
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

    /**
     * Matches all arguments that satisfy the provided predicate.
     */
    public static <T> T argThat(Predicate<T> pred) {
        var tClass = ((Class<?>)(pred.getClass().getTypeParameters()[0].getBounds()[0]));
        TarjunInfo.addArgumentMatcher((var o) -> (
                tClass.isInstance(o) &&
                pred.test((T) o))
        );
        return (T) defaultValueOf(tClass);
    }

    /**
     * Only matches null.
     */
    public static <T> T isNull() {
        TarjunInfo.addArgumentMatcher(Objects::isNull);
        return null;
    }


    /**
     * Matches any non-null argument.
     */
    public static <T> T notNull() {
        TarjunInfo.addArgumentMatcher(Objects::nonNull);
        return null;
    }

}
