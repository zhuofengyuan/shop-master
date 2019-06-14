package com.zhuofengyuan.wechat.shop.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {

    private String id;
    private String cartId;
    private BigDecimal price;
    private String productId;
}
