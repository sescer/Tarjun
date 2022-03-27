package ru.nsu.fit.g19213.tarjun.handler;

import java.lang.reflect.Method;


class MockHolder {
    private final Object[] args;
    private final Method method;
    private final Object retObj;
    private final DelegationStrategy delegationStrategy;


    MockHolder(Method method, Object[] args, Object retObj) {
        this.args = args;
        this.method = method;
        this.retObj = retObj;
        this.delegationStrategy = DelegationStrategy.RETURN_CUSTOM;
    }


    MockHolder(Method method, Object[] args) {
        this.args = args;
        this.method = method;
        this.retObj = null;
        this.delegationStrategy = DelegationStrategy.CALL_REAL_METHOD;

    }

    Object[] getArgs() {
        return args;
    }

    Method getMethod() {
        return method;
    }

    Object getRetObj() {
        return retObj;
    }

    DelegationStrategy getDelegationStrategy() {
        return delegationStrategy;
    }

}

