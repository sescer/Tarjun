package ru.nsu.fit.g19213.tarjun.matchers;

import ru.nsu.fit.g19213.tarjun.utils.TarjunInfo;

/**
 * Represents an argument matcher.<br/>
 * This means that given an argument it can check whether the passed argument is used in the current stubbing.<br/>
 * If you wish to implement this interface yourself, make a static method
 * that calls {@link TarjunInfo#addArgumentMatcher(ArgumentMatcher)}.<br/>
 * You should call this method exactly once and pass an ArgumentMatcher that you want to add.<br/>
 * Return value can be anything that can be cast to the given argument type.
 */
public interface ArgumentMatcher {
    boolean match(Object o);
}
