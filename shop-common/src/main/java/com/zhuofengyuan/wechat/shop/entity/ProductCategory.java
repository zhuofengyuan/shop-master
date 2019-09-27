package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("product_category")
public class ProductCategory {

    @TableId(type = IdType.NONE)
    private Long id;
    private String name;
    private Integer status;
    private String parent;
    private Integer sortOrder;
    private List<ProductCategory> children;
}
