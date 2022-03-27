package ru.nsu.fit.g19213.tarjun.examples;

import ru.nsu.fit.g19213.tarjun.Tarjun;
import ru.nsu.fit.g19213.tarjun.annotation.Mock;
import ru.nsu.fit.g19213.tarjun.annotation.MockAnnotation;
import ru.nsu.fit.g19213.tarjun.annotation.Spy;

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

        System.out.println(dog.getDog()); // Bobik
        Tarjun.when(dog.computeSum(5, 6)).thenReturn(5);
        System.out.println(dog.computeSum(5, 6)); // 5

        Tarjun.when(test.foo()).thenReturn("Mocked");
        Tarjun.when(test.bar(25)).thenReturn(100);

        System.out.println(test.foo()); // Mocked
        System.out.println(test.bar(25)); // 100
        System.out.println(test.bar(44)); // null

        System.out.println(test instanceof Test); // true
    }
}