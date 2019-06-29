package com.zhuofengyuan.wechat.shopweb.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class DaoInvocationHandler implements InvocationHandler {

    private Object object;

    public DaoInvocationHandler(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        runBefore();
        var m = method.invoke(object, args);
        runAfter();
        return m;
    }

    public abstract void runBefore();

    public abstract void runAfter();
}
