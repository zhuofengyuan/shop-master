package com.zhuofengyuan.wechat.shopweb.proxy;

public class DaoProxy extends DaoInvocationHandler{

    public DaoProxy(Object object) {
        super(object);
    }

    @Override
    public void runBefore() {
        System.out.println("运行前动作");
    }

    @Override
    public void runAfter() {
        System.out.println("运行后动作");
    }
}
