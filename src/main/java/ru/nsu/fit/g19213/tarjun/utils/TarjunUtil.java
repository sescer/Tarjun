package ru.nsu.fit.g19213.tarjun.utils;

import ru.nsu.fit.g19213.tarjun.matchers.ArgumentMatcher;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TarjunUtil {
    public static Object defaultValueOf(Class<?> klass) {
        if (klass == Integer.class || klass == int.class) {
            return 0;
        }
        if (klass == Double.class || klass == double.class) {
            return .0;
        }
        if (klass == Float.class || klass == float.class) {
            return .0f;
        }
        if (klass == Character.class || klass == char.class) {
            return '\0';
        }
        if (klass == Byte.class || klass == byte.class) {
            return 0;
        }
        if (klass == Long.class || klass == long.class) {
            return 0L;
        }
        if (klass == Short.class || klass == short.class) {
            return 0;
        }
        if (klass == Boolean.class || klass == boolean.class) {
            return false;
        }
        if (klass == String.class) {
            return "";
        }
        if (List.class.isAssignableFrom(klass)) {
            return List.of();
        }
        if (Map.class.isAssignableFrom(klass)) {
            return Map.of();
        }
        if (Array.class.isAssignableFrom(klass)) {
            return Array.newInstance(klass.getComponentType(), 0);
        }
        return null;
    }

    public static boolean matchArguments(Object[] expected, Object[] actual) {
        if (expected.length != actual.length) {
            return false;
        }

        if (Arrays.stream(expected).allMatch(ArgumentMatcher.class::isInstance)) {
            var flag = true;
            for (int i = 0; i < expected.length; i++) {
                if (!((ArgumentMatcher)expected[i]).match(actual[i])) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        return Arrays.deepEquals(expected, actual);
    }
}
