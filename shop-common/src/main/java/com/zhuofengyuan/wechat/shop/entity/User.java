package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private String id;
    private String openId;
    private String name;
    private String logo;
}
