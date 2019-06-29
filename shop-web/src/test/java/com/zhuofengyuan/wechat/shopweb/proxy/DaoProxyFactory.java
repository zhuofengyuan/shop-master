package com.zhuofengyuan.wechat.shopweb.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class DaoProxyFactory {

    public static <T> T getInstance(String className) {
        Object object;
        try {
            object = Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), new DaoProxy(object));
    }
}
