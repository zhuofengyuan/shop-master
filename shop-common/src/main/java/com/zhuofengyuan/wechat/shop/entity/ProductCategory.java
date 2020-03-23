package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("product_category")
public class ProductCategory {

    @TableId(type = IdType.NONE)
    private String id;
    private String name;
    private Integer status;
    private String parent;
    private String number;
    private Integer level;
    @TableField(exist = false)
    private String parentName;
    private Integer sortOrder;
    private String path;
    private Boolean isLeaf;
    @TableField(exist = false)
    private List<ProductCategory> children;
}
