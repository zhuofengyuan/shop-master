package com.zhuofengyuan.wechat.shop.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private String id;
    private String name;
    private BigDecimal price;
    private String desc;
}
