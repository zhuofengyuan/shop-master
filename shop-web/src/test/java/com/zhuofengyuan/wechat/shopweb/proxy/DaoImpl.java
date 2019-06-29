package com.zhuofengyuan.wechat.shopweb.proxy;

public class DaoImpl implements IDao {

    public DaoImpl() {}

    @Override
    public void insert() {
        System.out.println("这是一个插入方法");
    }

    @Override
    public void update() {
        System.out.println("这是一个编辑方法");
    }

    @Override
    public void select() {
        System.out.println("这是一个查询方法");
    }

    @Override
    public void delete() {
        System.out.println("这是一个删除方法");
    }
}
