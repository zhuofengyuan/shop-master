package com.zhuofengyuan.wechat.shopweb.proxy;

public class UserDao implements IDao{

    public UserDao() {}

    @Override
    public void insert() {
        System.out.println("User的插入方法");
    }

    @Override
    public void update() {
        System.out.println("User的编辑方法");
    }

    @Override
    public void select() {
        System.out.println("User的查询方法");
    }

    @Override
    public void delete() {
        System.out.println("User的删除方法");
    }
}
