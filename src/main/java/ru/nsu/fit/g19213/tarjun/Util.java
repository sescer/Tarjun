package ru.nsu.fit.g19213.tarjun;

public class Util {
    public static Object defaultValueOf(Class<?> klass) {
        if (klass == String.class) {
            return "";
        }
        if (klass == boolean.class) {
            return false;
        }
        if (klass == int.class || klass == Integer.class) {
            return 0;
        }
        if (klass == double.class) {
            return 0.0d;
        }
        if (klass == float.class) {
            return 0.0f;
        }
        if (klass == byte.class) {
            return 0;
        }
        if (klass == char.class) {
            return '\u0000';
        }
        if (klass == long.class) {
            return 0L;
        }
        if (klass == short.class) {
            return 0;
        }
        return null;
    }
}
