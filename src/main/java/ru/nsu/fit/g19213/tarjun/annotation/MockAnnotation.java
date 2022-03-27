package ru.nsu.fit.g19213.tarjun.annotation;

import ru.nsu.fit.g19213.tarjun.Tarjun;
import java.lang.reflect.Field;

public class MockAnnotation {
    public static void initMocks(Object instance) {

        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Mock.class)) {

                try {
                    field.setAccessible(true);
                    field.set(instance, Tarjun.mock(field.getType()));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else if (field.isAnnotationPresent(Spy.class)) {

                try {
                    field.setAccessible(true);

                    if (field.get(instance) != null)
                        field.set(instance, Tarjun.spy(field.get(instance)));
                    else
                        field.set(instance, Tarjun.spy(field.getType()));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
