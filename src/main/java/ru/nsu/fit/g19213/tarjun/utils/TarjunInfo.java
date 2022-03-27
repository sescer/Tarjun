package ru.nsu.fit.g19213.tarjun.utils;

import ru.nsu.fit.g19213.tarjun.handler.MockInvocationHandler;
import ru.nsu.fit.g19213.tarjun.matchers.ArgumentMatcher;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class TarjunInfo {

    private static final Queue<ArgumentMatcher> argumentMatcherQueue = new ArrayDeque<>();
    private static MockInvocationHandler lastMockInvocationHandler;

    public static MockInvocationHandler getLastMockInvocationHandler() {
        return lastMockInvocationHandler;
    }

    public static void setLastMockInvocationHandler(MockInvocationHandler handler) {
        lastMockInvocationHandler = handler;
    }

    public static void addArgumentMatcher(ArgumentMatcher am) {
        argumentMatcherQueue.add(am);
    }

    public static boolean isArgumentMatcherQueueEmpty() {
        return argumentMatcherQueue.isEmpty();
    }

    public static ArgumentMatcher[] getArgumentMatchers() {
        var arr = Arrays.stream(argumentMatcherQueue.toArray()).toArray(ArgumentMatcher[]::new);
        argumentMatcherQueue.clear();
        return arr;
    }
}
