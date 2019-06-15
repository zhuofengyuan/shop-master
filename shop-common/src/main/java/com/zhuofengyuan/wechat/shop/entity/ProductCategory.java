package com.zhuofengyuan.wechat.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_category")
public class ProductCategory {

    private String id;
    private String name;
    private Integer status;
}
