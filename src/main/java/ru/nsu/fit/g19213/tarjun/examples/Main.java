package ru.nsu.fit.g19213.tarjun.examples;

import ru.nsu.fit.g19213.tarjun.annotation.Mock;
import ru.nsu.fit.g19213.tarjun.annotation.MockAnnotation;
import ru.nsu.fit.g19213.tarjun.annotation.Spy;

import static ru.nsu.fit.g19213.tarjun.Tarjun.when;
import static ru.nsu.fit.g19213.tarjun.matchers.ArgumentMatchers.*;

public class Main {
    @Mock
    Test test;

    @Spy
    private final Dog dog = new Dog("Bobik");


    public static void main(String[] args) {
        Main main = new Main();
        main.test();
    }
    private void test() {
        MockAnnotation.initMocks(this);

        when(dog.computeSum(5, 6)).thenReturn(5);

        System.out.println("Dog test:");
        System.out.println(dog.getDog()); // Bobik
        System.out.println(dog.computeSum(5, 6)); // 5

        when(test.foo()).thenReturn("Mocked");
        when(test.bar(25)).thenReturn(100);

        when(test.multi(anyInt(), eq(10))).thenReturn(42);
        when(test.multi(eq(10), anyInt())).thenReturn(1337);
        when(test.multi(10, 10)).thenReturn(7);

        System.out.println("Test test:");
        System.out.println(test.foo()); // Mocked

        System.out.println(test.bar(25)); // 100
        System.out.println(test.bar(44)); // 0

        System.out.println(test.multi(10, 10)); // 7
        System.out.println(test.multi(1, 10)); // 42
        System.out.println(test.multi(10, 1)); // 1337
        System.out.println(test.multi(1, 1)); // 0

        System.out.println(test instanceof Test); // true
    }
}