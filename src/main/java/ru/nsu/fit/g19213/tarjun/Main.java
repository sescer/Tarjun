package ru.nsu.fit.g19213.tarjun;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Test test = Tarjun.mock(Test.class);
        Tarjun.when(test.foo()).thenReturn("Mocked!");
        Tarjun.when(test.bar(25)).thenReturn(35);

        System.out.println(test.foo()); // Mocked!
        System.out.println(test.bar(25)); // 35
        System.out.println(test.bar(44)); // null

        System.out.println(test instanceof Test);
    }
}