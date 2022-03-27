package ru.nsu.fit.g19213.tarjun.stubbing;
import ru.nsu.fit.g19213.tarjun.utils.TarjunInfo;

public class Stubber<T> {

    public void thenReturn(T retObj) {
        TarjunInfo.getLastMockInvocationHandler().setRetObj(retObj);
    }

    public void invokeRealMethod() {
        TarjunInfo.getLastMockInvocationHandler().setRealMethodInvocation();
    }
}