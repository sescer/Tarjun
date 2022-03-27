package ru.nsu.fit.g19213.tarjun.handler;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import ru.nsu.fit.g19213.tarjun.utils.TarjunInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class MockInvocationHandler {

    private final List<MockHolder> mockHolders = new ArrayList<>();
    private Method lastMethod = null;
    private Object[] lastArgs = null;
    private final DelegationStrategy delegationStrategy;

    public MockInvocationHandler(DelegationStrategy delegationStrategy) {
        this.delegationStrategy = delegationStrategy;
    }

    @RuntimeType
    public Object invoke(@SuperCall Callable<?> zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {

        TarjunInfo.setLastMockInvocationHandler(this);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (!stackTraceElements[2].toString().contains(stackTraceElements[3].getClassName())) {
            lastMethod = method;
            lastArgs = args;
        }

        for (MockHolder mockHolder : mockHolders) {
            if (mockHolder.getMethod().equals(method) && Arrays.deepEquals(mockHolder.getArgs(), args)) {
                switch (mockHolder.getDelegationStrategy()) {
                    case CALL_REAL_METHOD:
                        return zuper.call();

                    case RETURN_CUSTOM:
                        return mockHolder.getRetObj();
                }
            }
        }

        if (delegationStrategy == DelegationStrategy.CALL_REAL_METHOD)
            return zuper.call();
        else
            return null;
    }

    public void setRetObj(Object retObj) {

        mockHolders.removeIf(mockHolder -> mockHolder.getMethod().equals(lastMethod) &&
                Arrays.deepEquals(mockHolder.getArgs(), lastArgs));
        mockHolders.add(new MockHolder(lastMethod, lastArgs, retObj));
    }

    public void setRealMethodInvocation() {
        mockHolders.removeIf(mockHolder -> mockHolder.getMethod().equals(lastMethod) &&
                Arrays.deepEquals(mockHolder.getArgs(), lastArgs));
        mockHolders.add(new MockHolder(lastMethod, lastArgs));
    }
}
