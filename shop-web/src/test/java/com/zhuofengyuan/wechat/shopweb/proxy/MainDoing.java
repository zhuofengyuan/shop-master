package com.zhuofengyuan.wechat.shopweb.proxy;

import java.lang.reflect.Proxy;

public class MainDoing {

    public static void main(String[] args) {

        IDao userDao = DaoProxyFactory.getInstance("com.zhuofengyuan.wechat.shopweb.proxy.UserDao");
        userDao.insert();
        userDao.update();
        userDao.select();
        userDao.delete();

        System.out.println("------------------------------------------------------");
        IDao baseDao = DaoProxyFactory.getInstance("com.zhuofengyuan.wechat.shopweb.proxy.DaoImpl");
        baseDao.insert();
        baseDao.update();
        baseDao.insert();
        baseDao.delete();
    }
}
