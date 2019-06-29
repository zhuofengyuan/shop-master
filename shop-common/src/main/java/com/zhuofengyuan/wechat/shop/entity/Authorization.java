package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("authorization")
public class Authorization {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String code;
    private String name;
}
