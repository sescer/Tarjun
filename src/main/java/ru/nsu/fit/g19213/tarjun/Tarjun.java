package ru.nsu.fit.g19213.tarjun;

import ru.nsu.fit.g19213.tarjun.creation.MockCreator;
import ru.nsu.fit.g19213.tarjun.handler.DelegationStrategy;
import ru.nsu.fit.g19213.tarjun.stubbing.Stubber;

public class Tarjun {

    public static <T> T mock(Class<T> klass) {
        return MockCreator.mock(klass, DelegationStrategy.RETURN_DEFAULT);
    }

    public static <T> T spy(Class<T> klass) {
        return MockCreator.mock(klass, DelegationStrategy.CALL_REAL_METHOD);
    }

    public static <T> T spy(T obj) {
        return MockCreator.spy(obj);
    }

    public static <T> Stubber<T> when(T obj) {
        return new Stubber<>();
    }
}