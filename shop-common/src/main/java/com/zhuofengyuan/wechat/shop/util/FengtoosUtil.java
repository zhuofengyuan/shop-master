package com.zhuofengyuan.wechat.shop.util;

import com.zhuofengyuan.wechat.shop.exception.FengtoosException;

public class FengtoosUtil {

    public static void null2Entity(Object entity, String msg){
        if(entity == null){
            throw new FengtoosException(500, msg);
        }
    }

    public static void null2Entity(Object entity){
        null2Entity(entity, "对象不能为空");
    }

}
