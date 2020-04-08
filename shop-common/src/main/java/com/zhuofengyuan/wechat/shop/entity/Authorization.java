package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("authorization")
public class Authorization {

    @TableId(type = IdType.NONE)
    private String id;
    private String code;
    private String name;
    private Integer status;
    private Integer level;
    @TableField(exist = false)
    private String parentName;
    private Integer sortOrder;
    private String path;
    private Boolean isLeaf;
    private String parent;
    private String description;
    private String url;
    private String icon;
    @TableField(exist = false)
    private List<Authorization> children;
}
