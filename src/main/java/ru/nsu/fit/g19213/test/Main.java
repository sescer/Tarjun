package ru.nsu.fit.g19213.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static ru.nsu.fit.g19213.tarjun.Tarjun.mock;
import static ru.nsu.fit.g19213.tarjun.Tarjun.when;
import static ru.nsu.fit.g19213.tarjun.argumentmatchers.ArgumentMatchers.anyIn;
import static ru.nsu.fit.g19213.tarjun.argumentmatchers.ArgumentMatchers.anyInt;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Test test = mock(Test.class);
        when(test.foo()).thenReturn("Mocked!");
        when(test.bar(anyInt())).thenReturn(123);
        when(test.bar(25)).thenReturn(35);

        when(test.shout(anyIn("hello", "hi"))).thenReturn("Greetings!");
        when(test.doubleUp(anyInt())).thenReturn(7);

        System.out.println(test.foo()); // Mocked!
        System.out.println(test.bar(25)); // 35
        System.out.println(test.bar(44)); // 123

        System.out.println(test.shout("hi")); // Greetings!
        System.out.println(test.shout("hello")); // Greetings!
        System.out.println(test.shout("hey!")); // ""

        System.out.println(test.doubleUp(42)); // 7
        System.out.println(test.doubleUp(4)); // 7


        System.out.println("ArrayList:");
        ArrayList<Integer> al = mock(ArrayList.class);
        when(al.get(anyInt())).thenReturn(42);

        System.out.println(al.get(1));  // 42
        System.out.println(al.get(12)); // 42
    }
}