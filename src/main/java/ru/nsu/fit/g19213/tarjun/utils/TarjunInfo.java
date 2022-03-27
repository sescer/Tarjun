package ru.nsu.fit.g19213.tarjun.utils;

import ru.nsu.fit.g19213.tarjun.handler.MockInvocationHandler;

public class TarjunInfo {

    private static MockInvocationHandler lastMockInvocationHandler;

    public static MockInvocationHandler getLastMockInvocationHandler() {
        return lastMockInvocationHandler;
    }

    public static void setLastMockInvocationHandler(MockInvocationHandler handler) {
        lastMockInvocationHandler = handler;
    }
}
