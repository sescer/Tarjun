package ru.nsu.fit.g19213.test;

public class Test implements TestInterface {
    @Override
    public String foo() {
        return "Foo";
    }

    @Override
    public int bar(Integer num) {
        return num;
    }

    @Override
    public void tmp() {

    }

    public int doubleUp(int a) {
        return a * 2;
    }

    public String shout(String str) {
        return str.toUpperCase();
    }

}