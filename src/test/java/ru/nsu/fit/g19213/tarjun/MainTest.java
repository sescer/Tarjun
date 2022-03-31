package ru.nsu.fit.g19213.tarjun;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.nsu.fit.g19213.tarjun.annotation.MockAnnotation;
import ru.nsu.fit.g19213.tarjun.annotation.Spy;

import static ru.nsu.fit.g19213.tarjun.Tarjun.when;
import static ru.nsu.fit.g19213.tarjun.matchers.ArgumentMatchers.*;

public class MainTest {

    private SomeTestClass test;

    @Spy
    private final Dog dog = new Dog("Bobik");

    @BeforeEach
    public void setup(){
        MockAnnotation.initMocks(this);
        test = Tarjun.mock(SomeTestClass.class);
    }

    @Test
    public void shouldCreateSpyObject() {
        Tarjun.spy(new Object());
    }

    @Test
    public void shouldStub(){
        when(test.foo()).thenReturn("Tarjun");
        Assertions.assertEquals("Tarjun", test.foo());
    }

    @Test
    public void shouldOverrideStub(){
        when(test.bruh(anyInt())).thenReturn("Kock");
        when(test.bruh(228)).thenReturn("TarjunMyBeloved");

        Assertions.assertEquals("Kock", test.bruh(1337));
        Assertions.assertEquals("TarjunMyBeloved", test.bruh(228));
    }
    @Test
    public void shouldCallRealMethod() {
        when(test.bruh(228)).invokeRealMethod();
        Assertions.assertEquals("Bruh", test.bruh(228));
    }
    @Test
    public void shouldThrow() {
        when(test.foo()).thenThrow(new IllegalArgumentException("Exception occurred"));
        try{
            test.foo();
        }catch (Throwable t) {
            Assertions.assertEquals("Exception occurred", t.getMessage());
        }
    }

    @Test
    public void shouldStubSpy(){
        when(dog.computeSum(5, 6)).thenReturn(5);
        Assertions.assertEquals(5, dog.computeSum(5, 6));
    }

    @Test
    public void shouldOverrideStubSpy(){
        when(dog.computeSum(anyInt(), anyInt())).thenReturn(1337);
        when(dog.computeSum(228, 1337)).thenReturn(666);
        Assertions.assertEquals(1337, dog.computeSum(22,8));
        Assertions.assertEquals(666, dog.computeSum(228,1337));
    }

    @Test
    public void shouldCallRealMethodSpy() {
        when(dog.computeSum(1,2)).invokeRealMethod();
        Assertions.assertEquals(3, dog.computeSum(1,2));
    }

    @Test
    public void shouldThrowSpy() {
        when(dog.getDog()).thenThrow(new IllegalArgumentException("Exception occurred when get name of dog"));
        try{
            dog.getDog();
        }catch (Throwable t) {
            Assertions.assertEquals("Exception occurred when get name of dog", t.getMessage());
        }
    }

    @Test
    public void shouldMatching(){
        when(test.multi(anyInt(), eq(10))).thenReturn(42);
        when(test.multi(eq(10), anyInt())).thenReturn(1337);
        when(test.multi(10, 10)).thenReturn(7);

        Assertions.assertEquals(7 ,test.multi(10, 10));
        Assertions.assertEquals(42 ,test.multi(1, 10));
        Assertions.assertEquals(1337 ,test.multi(10, 1));
        Assertions.assertEquals(0 ,test.multi(1, 1));


    }
}