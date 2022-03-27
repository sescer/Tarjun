package ru.nsu.fit.g19213.tarjun.examples;

public class Test implements TestInterface {
    @Override
    public String foo() {
        return "Foo";
    }

    @Override
    public Integer bar(Integer num) {
        return num;
    }

    @Override
    public void tmp() {

    }

    public int multi(int a, int b) {
        return a * b;
    }
}
