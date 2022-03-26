package ru.nsu.fit.g19213.tarjun;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import ru.nsu.fit.g19213.tarjun.argumentmatchers.ArgumentMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Tarjun {

    public static Queue<ArgumentMatcher> currentStubbingMatcherQueue = new ArrayDeque<>();

    private static MockInvocationHandler lastMockInvocationHandler;

    public static <T> T mock(Class<T> klass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(klass)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(new MockInvocationHandler()))
                .make()
                .load(klass.getClassLoader())
                .getLoaded();

        return byteBuddy.getDeclaredConstructor().newInstance();
    }

    public static <T> When<T> when(T obj) {
        return new When<>();
    }

    public static class When<T> {
        public void thenReturn(T retObj) {
            lastMockInvocationHandler.setRetObj(retObj);
        }
    }

    private static class MockInvocationHandler implements InvocationHandler {

        private Method lastMethod;
        private Object[] lastArgs;
        private Map<Method, Map<Object[], Object>> methodMap = new HashMap<>();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            lastMockInvocationHandler = this;
            lastMethod = method;
            lastArgs = args;
            if (!currentStubbingMatcherQueue.isEmpty()) {
                if (args.length != currentStubbingMatcherQueue.size()) {
                    throw new IllegalArgumentException("If you use matchers all the passed arguments have to matchers.");
                }
                return Util.defaultValueOf(method.getReturnType());
            }

            if (methodMap.containsKey(method)) {
                var methodVal = methodMap.get(method);
                Object currRes = null;
                boolean foundArgMatch = false;
                for (var i : methodVal.entrySet()) {
                    var argArr = i.getKey();
                    if (Arrays.deepEquals(argArr, args)) {
                        return i.getValue();
                    }
                    if (Arrays.stream(argArr).allMatch(ArgumentMatcher.class::isInstance)) {
                        boolean flag = true;
                        for (int j = 0; j < argArr.length; j++) {
                            if (!((ArgumentMatcher)argArr[j]).check(args[j])) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            foundArgMatch = true;
                            currRes = i.getValue();
                        }
                    }
                }
                if (foundArgMatch)
                    return currRes;
            }

            return Util.defaultValueOf(method.getReturnType());
        }

        public void setRetObj(Object retObj) {
            methodMap.computeIfAbsent(lastMethod, (Method m) -> new HashMap<>());
            if (currentStubbingMatcherQueue.isEmpty())
                methodMap.get(lastMethod).put(lastArgs, retObj);
            else {
                List<ArgumentMatcher> argumentMatchers = new ArrayList<>();
                while(!currentStubbingMatcherQueue.isEmpty()) {
                    argumentMatchers.add(currentStubbingMatcherQueue.remove());
                }
                methodMap.get(lastMethod).put(argumentMatchers.toArray(), retObj);
            }
        }
    }
}