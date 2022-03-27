package ru.nsu.fit.g19213.tarjun.creation;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import ru.nsu.fit.g19213.tarjun.utils.TarjunInfo;
import ru.nsu.fit.g19213.tarjun.handler.DelegationStrategy;
import ru.nsu.fit.g19213.tarjun.handler.MockInvocationHandler;

import java.lang.reflect.Field;

public class MockCreator {

    public static <T> T mock(Class<T> klass, DelegationStrategy delegationStrategy) {
        TarjunInfo.setLastMockInvocationHandler(new MockInvocationHandler(delegationStrategy));

        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(klass)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(TarjunInfo.getLastMockInvocationHandler()))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
        return thingyInstantiator.newInstance();
    }

    public static <T> T spy(T obj) {
        TarjunInfo.setLastMockInvocationHandler(new MockInvocationHandler(DelegationStrategy.CALL_REAL_METHOD));

        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass((Class<T>) obj.getClass())
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(TarjunInfo.getLastMockInvocationHandler()))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
        T instance = thingyInstantiator.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {

                field.setAccessible(true);
                Object value = field.get(obj);
                if (value != null) {

                    Field fieldInstance = instance.getClass().getSuperclass().getDeclaredField(field.getName());
                    fieldInstance.setAccessible(true);

                    fieldInstance.set(instance, value);

                }

            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return instance;

    }
}
